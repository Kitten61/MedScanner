package com.test.medscanner.respositoryImpl;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.test.medscanner.repository.SearchRepository;
import com.test.medscanner.util.Clinic;
import com.test.medscanner.util.FavoriteService;
import com.test.medscanner.util.Note;
import com.test.medscanner.util.Service;
import com.yandex.mapkit.geometry.Geo;
import com.yandex.mapkit.geometry.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.rxjava3.core.Single;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class SearchRepositoryImpl extends FavoriteRepositoryImpl implements SearchRepository {
    @Override
    public Single<Boolean> loadDataFromFirebase() {
        return Single.create(
                subscriber -> {
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                    firebaseFirestore
                            .collection("services")
                            .get()
                            .addOnSuccessListener(
                                    queryDocumentSnapshots -> {
                                        List<DocumentSnapshot> d = queryDocumentSnapshots.getDocuments();
                                        Log.e("TESSST", d.size() + " services snapshots");
                                        Realm realm = Realm.getDefaultInstance();
                                        realm.executeTransaction(
                                                r -> {
                                                    r.delete(Service.class);
                                                    for (DocumentSnapshot snapshot : d) {
                                                        Service object = r.createObject(Service.class);
                                                        object.setCategory((String) snapshot.get("category"));
                                                        object.setHospital_id((String) snapshot.get("hospital_id"));
                                                        object.setId((String) snapshot.get("id"));
                                                        object.setName((String) snapshot.get("name"));
                                                        object.setPrice((long) snapshot.get("price"));
                                                    }
                                                    Log.e("TESSST", r.where(Service.class).findAll().size() + " services added");
                                                }
                                        );
                                    }
                            );
                    firebaseFirestore
                            .collection("hospitals")
                            .get()
                            .addOnSuccessListener(
                                    queryDocumentSnapshots -> {
                                        List<DocumentSnapshot> d = queryDocumentSnapshots.getDocuments();
                                        Realm realm = Realm.getDefaultInstance();
                                        realm.executeTransaction(
                                                r -> {
                                                    r.delete(Clinic.class);
                                                    for (DocumentSnapshot snapshot : d) {
                                                        Clinic object = r.createObject(Clinic.class);
                                                        object.setAdress((String) snapshot.get("address"));

                                                        List<Double> coordinates = (ArrayList<Double>) snapshot.get("coordinates");
                                                        Double[] coordinatesArray = coordinates.toArray(new Double[coordinates.size()]);
                                                        object.setLatitude(coordinatesArray[0]);
                                                        object.setLongtitude(coordinatesArray[1]);

                                                        object.setId((String) snapshot.get("id"));
                                                        object.setName((String) snapshot.get("name"));
                                                        object.setPhoneNumber((String) snapshot.get("phoneNumber"));
                                                        object.setPhoto((String) snapshot.get("photo"));

                                                        List<String> schedule = (ArrayList<String>) snapshot.get("schedule");
                                                        Log.e("TESSST", object.getId() + "  " + schedule.toString());
                                                        object.setSchedule(new RealmList<String>(schedule.toArray(new String[schedule.size()])));

                                                        object.setWebSite((String) snapshot.get("webSite"));
                                                    }
                                                    Log.e("TESSST", r.where(Clinic.class).findAll().size() + " clinics added");
                                                }
                                        );
                                    }
                            );
                    subscriber.onSuccess(true);

                }
        );
    }

    @Override
    public Single<List<Clinic>> loadDataFromLocalDB(String query, Point location) {
        return Single.create(
                subscriber -> {
                    Realm realm = Realm.getDefaultInstance();

                    RealmResults<Service> resultsServiceQuery = realm.where(Service.class).findAll();
                    List<Service> servicesList = realm.copyFromRealm(resultsServiceQuery);

                    List<String> queryList = Arrays.asList(query.split(" "));

                    HashMap<String, Clinic> resultHashMap = new HashMap<>();

                    serviceLoop:
                    for (Service service : servicesList) {

                        String serviceName = service.getName();
                        for (String tag : queryList) {
                            if (!serviceName.toLowerCase().contains(tag.toLowerCase()))
                                continue serviceLoop;
                        }

                        String serviceHospitalId = service.getHospital_id();

                        if (!resultHashMap.containsKey(serviceHospitalId)) {
                            Clinic clinic = realm.copyFromRealm(
                                    Objects.requireNonNull(realm.where(Clinic.class)
                                            .equalTo("id", serviceHospitalId)
                                            .findFirst())
                            );
                            Log.e("TESSST", Geo.distance(location, new Point(clinic.getLatitude(), clinic.getLongtitude()))+"");
                            resultHashMap.put(serviceHospitalId, clinic);
                        }

                        Clinic clinic = resultHashMap.get(serviceHospitalId);

                        FavoriteService serv = realm.where(FavoriteService.class).equalTo("id", service.getId()).findFirst();
                        if (serv != null) {
                            service.setStarred(true);
                        } else {
                            service.setStarred(false);
                        }

                        clinic.putService(service);
                    }

                    List<Clinic> result = new ArrayList<>();
                    for (Map.Entry<String, Clinic> map : resultHashMap.entrySet()) {
                        result.add(map.getValue());
                    }

                    realm.close();
                    subscriber.onSuccess(result);
                }
        );
    }
}

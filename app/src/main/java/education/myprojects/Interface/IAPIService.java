package education.myprojects.Interface;

import java.util.ArrayList;

import education.myprojects.model.API.TranslateTuVung;
import education.myprojects.model.API.TuVung;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAPIService {
    String Trans_Service = "https://api.tracau.vn/WBBcwnwQpV89/s/";
    String BASE_Service = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    @GET("{id}/en")
    Observable<TranslateTuVung> GetTrans(@Path("id") String id);

    @GET("{id}")
    Observable<ArrayList<TuVung>> GetTuDien(@Path("id") String id);
}

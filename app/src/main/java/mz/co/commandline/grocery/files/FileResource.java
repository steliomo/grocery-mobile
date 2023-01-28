package mz.co.commandline.grocery.files;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FileResource {

    @GET("files/{fileName}")
    Call<ResponseBody> loadPdfFile(@Path("fileName") String fileName);

}

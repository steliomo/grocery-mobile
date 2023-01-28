package mz.co.commandline.grocery.files;

import javax.inject.Inject;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.rent.service.RentResource;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileServiceImpl extends AbstractService implements FileService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public FileServiceImpl() {
    }

    @Override
    public FileResource getResource() {
        return retrofitService.getResource(FileResource.class);
    }

    @Override
    public void loadPdfFile(String fileName, final ResponseListner<ResponseBody> responseListner) {
        getResource().loadPdfFile(fileName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}

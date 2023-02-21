package mz.co.commandline.grocery.guide.service;

import javax.inject.Inject;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.generics.service.AbstractService;
import mz.co.commandline.grocery.generics.service.RetrofitService;
import mz.co.commandline.grocery.rent.dto.GuideDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuideServiceImpl extends AbstractService implements GuideService {

    @Inject
    RetrofitService retrofitService;

    @Inject
    public GuideServiceImpl() {
    }

    @Override
    public GuideResource getResource() {
        return retrofitService.getResource(GuideResource.class);
    }

    @Override
    public void issueTransportGuide(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner) {
        getResource().issueTransportGuide(guideDTO).enqueue(new Callback<GuideDTO>() {
            @Override
            public void onResponse(Call<GuideDTO> call, Response<GuideDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<GuideDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void issueReturnGuide(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner) {
        getResource().issueReturnGuide(guideDTO).enqueue(new Callback<GuideDTO>() {
            @Override
            public void onResponse(Call<GuideDTO> call, Response<GuideDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<GuideDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void issueDeliveryGuide(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner) {
        getResource().issueDeliveryGuide(guideDTO).enqueue(new Callback<GuideDTO>() {
            @Override
            public void onResponse(Call<GuideDTO> call, Response<GuideDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<GuideDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }

    @Override
    public void issueGuidePDF(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner) {
        getResource().issueGuidePDF(guideDTO).enqueue(new Callback<GuideDTO>() {
            @Override
            public void onResponse(Call<GuideDTO> call, Response<GuideDTO> response) {
                if (response.isSuccessful()) {
                    responseListner.success(response.body());
                    return;
                }

                setBodyError(response, responseListner);
            }

            @Override
            public void onFailure(Call<GuideDTO> call, Throwable t) {
                responseListner.error(t.getMessage());
            }
        });
    }
}

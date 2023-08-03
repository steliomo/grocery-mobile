package mz.co.commandline.grocery.guide.service;

import mz.co.commandline.grocery.guide.dto.GuideDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GuideResource {

    @POST("guides/issue-transport-guide")
    Call<GuideDTO> issueTransportGuide(@Body GuideDTO guideDTO);

    @POST("guides/issue-return-guide")
    Call<GuideDTO> issueReturnGuide(@Body GuideDTO guideDTO);

    @POST("guides/issue-delivery-guide")
    Call<GuideDTO> issueDeliveryGuide(@Body GuideDTO guideDTO);

    @POST("guides/issue-guide-pdf")
    Call<GuideDTO> issueGuidePDF(@Body GuideDTO guideDTO);
}

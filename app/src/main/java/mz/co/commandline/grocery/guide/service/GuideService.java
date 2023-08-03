package mz.co.commandline.grocery.guide.service;

import mz.co.commandline.grocery.generics.listner.ResponseListner;
import mz.co.commandline.grocery.guide.dto.GuideDTO;

public interface GuideService {

    void issueTransportGuide(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner);

    void issueReturnGuide(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner);

    void issueDeliveryGuide(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner);

    void issueGuidePDF(GuideDTO guideDTO, ResponseListner<GuideDTO> responseListner);
}

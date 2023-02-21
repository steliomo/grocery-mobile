package mz.co.commandline.grocery.guide.delegate;

import java.util.List;

import mz.co.commandline.grocery.rent.dto.GuideDTO;

public interface GuideDelegate {
    List<GuideDTO> getGuidesDTO();

    void selectedGuide(GuideDTO guideDTO);

    GuideDTO getGuideDTO();

    void reIssueGuide();
}

package mz.co.commandline.grocery.rent.dto

import mz.co.commandline.grocery.R

enum class GuideType(public val value: Int) {

    RETURN(R.string.devolution_guide),

    TRANSPORT(R.string.transport_guide),

    DELIVERY(R.string.delivery_guide);
}

package com.example.mycasektx.data.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class RequestResponse(
    val id:Long? = null,
    val caseTitle: String? = null,
    val court:String? = null,
    val caseType:String? = null,
    val clientName:String? = null,
    val clientAddress:String? = null,
    val clientDescription:String? = null,
    val opposingPartyName:String? = null,
    val opposingPartyAddress:String? = null,
    val opposingPartyDescription:String? = null,
    var isPaid:Boolean = false,
    var isReviewed:Boolean = false,
    val files:ArrayList<String?>? = null,
    val lawyerName:String? = null,
    val lawyerEmail:String? = null,
):Parcelable

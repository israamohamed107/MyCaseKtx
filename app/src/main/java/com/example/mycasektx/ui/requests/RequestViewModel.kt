package com.example.mycasektx.ui.requests

import android.util.Log
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.example.mycasektx.data.responses.RequestResponse
import com.example.mycasektx.ui.COURT
import com.example.mycasektx.ui.LAWYER
import com.example.mycasektx.ui.REQUESTS
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val databaseReference = FirebaseDatabase.getInstance().getReference()
    val requestItem = savedStateHandle.get<RequestResponse>("request")

    private var _requestStatus: MutableLiveData<Boolean> = MutableLiveData()
    val requestStatus: LiveData<Boolean> = _requestStatus

    private var requestsList = ArrayList<RequestResponse?>()

    private var _requests: MutableLiveData<ArrayList<RequestResponse?>> = MutableLiveData()
    val requests: LiveData<ArrayList<RequestResponse?>> = _requests

    private var _reviewedRequests: MutableLiveData<ArrayList<RequestResponse?>> = MutableLiveData()
    val reviewedRequests: LiveData<ArrayList<RequestResponse?>> = _reviewedRequests


    private var _paidRequests: MutableLiveData<ArrayList<RequestResponse?>> = MutableLiveData()
    val paidRequests: LiveData<ArrayList<RequestResponse?>> = _paidRequests


    fun submitResponse(requestResponse: RequestResponse, userEmail: String) {
        val id = databaseReference.push().key.toString()
        viewModelScope.launch {
            databaseReference.child(LAWYER).child(REQUESTS).child(userEmail).child(id)
                .setValue(requestResponse).addOnCompleteListener {
                _requestStatus.postValue(true)

            }.addOnFailureListener {
                _requestStatus.postValue(false)
            }

        }



    }

    fun getAllRequests(userPhone: String) {
        viewModelScope.launch {
            databaseReference.child(LAWYER).child(REQUESTS).child(userPhone)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            requestsList.clear()
                            for (sp in snapshot.children) {
                                requestsList.add(sp.getValue(RequestResponse::class.java))
                            }
                            _requests.postValue(requestsList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
        }
    }

    fun getReviewedRequests(userPhone: String) {
        viewModelScope.launch {
            databaseReference.child(COURT).child(REQUESTS).child(userPhone)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            requestsList.clear()
                            for (sp in snapshot.children) {
                                val request = sp.getValue(RequestResponse::class.java)
                                if(request?.isPaid == false && request?.isReviewed == true){
                                    requestsList.add(request)
                                }
                            }
                            _reviewedRequests.postValue(requestsList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
        }
    }

    fun getPaidRequests(userPhone: String) {
        viewModelScope.launch {
            databaseReference.child(COURT).child(REQUESTS).child(userPhone)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            requestsList.clear()
                            for (sp in snapshot.children) {

                                val request = sp.getValue(RequestResponse::class.java)
                                if(request?.isPaid == true && request?.isReviewed == true){
                                    requestsList.add(request)
                                }
                            }
                            _paidRequests.postValue(requestsList)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
        }
    }



}
package com.example.kotlinproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*


class Login : AppCompatActivity(), View.OnClickListener {
    private val RC_SIGN_IN:Int = 100
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Google 로그인 설정
        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        login_google.setOnClickListener(this)
        login_signin.setOnClickListener(this)
    }

    //Google 로그인 결과값 check
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    //Google 로그인 성공여부 처리
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        Log.e("HandleSignInResult","실행")
        try {
            val account =
                completedTask.getResult(ApiException::class.java)

            getAuthWithGoogle(account)
        } catch (e: ApiException) {
            Log.e("HandleSignInResult", "signInResult:failed code=" + e.statusCode)
            getAuthWithGoogle(null)
        }
    }

    //로그인 최종 처리
    fun getAuthWithGoogle(acct: GoogleSignInAccount?) {
        Log.e("getAuthWithGoogle","실행")
        if (acct != null) {
            val personName = acct.displayName
            val personGivenName = acct.givenName
            val personFamilyName = acct.familyName
            val personEmail = acct.email
            val personId = acct.id
            val personPhoto: Uri? = acct.photoUrl
            Toast.makeText(this, "personEmail : $personEmail personId : $personId",Toast.LENGTH_LONG).show()
        }
    }

    //click 이벤트 처리
    override fun onClick(v:View?){
        when(v?.id){
            R.id.login_google->{
                googleSignIn()
            }
            R.id.login_signin->{
                Toast.makeText(this, "로그아웃",Toast.LENGTH_SHORT).show()
                googleSignOut()
            }
        }
    }

    //google login
    fun googleSignIn(){
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    //log out
    fun googleSignOut () {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                Toast.makeText(this, "로그아웃",Toast.LENGTH_SHORT).show()
            }
    }

    //disconnect
    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                //...
            }
    }
}
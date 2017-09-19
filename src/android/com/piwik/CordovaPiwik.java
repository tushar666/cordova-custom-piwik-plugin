package com.piwik;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.piwik.sdk.Piwik;
import org.piwik.sdk.TrackHelper;
import org.piwik.sdk.Tracker;

import java.net.MalformedURLException;
import java.util.Date;

public class CordovaPiwik extends CordovaPlugin {
  private static final String TAG = "CordovaPiwik";
  public static final String STR_SLASH = "/";
  private  Tracker piwikTracker = null;


  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    Log.d(TAG, "Initializing PiwikCordovaPlugin");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (action.equals("start_track")) {
      String trackerUrl;
      int applicationId;
      String userId;
      try {
        trackerUrl = args.getString(0);
      } catch (JSONException ex) {
        callbackContext.error("Tracker URL does not appear to be a valid String");
        return true;
      }
      try {
        applicationId = args.getInt(1);
      } catch (JSONException ex) {
        callbackContext.error("Appilcation id does not appear to be a valid integer");
        return true;
      }
      userId = args.opt(2) != null ? args.optString(2) : "";

      this.startTracker(trackerUrl, applicationId, userId);
    } else if (action.equals("page")) {
      String phrase1 = args.getString(0);
      String phrase2 = args.getString(1);
      // Echo back the first argument
      piwikSendEvents(args.getString(0),"page",cordova.getActivity().getWindow().getContext());
      /*Log.d(TAG, phrase);
      Toast.makeText(cordova.getActivity().getWindow().getContext(), "Yo Man! Its working ", Toast.LENGTH_LONG).show();*/
    } else if (action.equals("link")) {
      // An example of returning data back to the web layer
      piwikSendEvents(args.getString(0),"link",cordova.getActivity().getWindow().getContext());
    }
    return true;
  }



  public void piwikSendEvents(String label, String action, Context ctx) {
    //for post login set getTraker parameter to true -- true for customerId
    if (piwikTracker != null) {
      TrackHelper.Screen screen = TrackHelper.track().screen(STR_SLASH + label).title(action);
      TrackHelper.Screen screenwithvariable = setCustomVariablePostLogin(screen, ctx);
      piwikTracker.setDispatchInterval(-1);
      screenwithvariable.with(piwikTracker);
      piwikTracker.dispatch();
    }
  }
  private TrackHelper.Screen setCustomVariablePostLogin(TrackHelper.Screen tracker, Context ctx) {
    //PiwikCustomVariableDTO dto = ((BaseActivity) ctx).getPiwikDataFromJSON();
    tracker.variable(1, PiwikConstant.CUSTOM_VARIABLE_IMEI_NO, "1111");
    tracker.variable(2, PiwikConstant.CUSTOM_VARIABLE_APP_VERSION, "0.0.1");
//    tracker.variable(3, PiwikConstant.CUSTOM_VARIABLE_PLATFORM, "Android");
//    tracker.variable(4, PiwikConstant.CUSTOM_VARIABLE_MOBILE_NO, dto.getMobile_no());
//    tracker.variable(5, PiwikConstant.CUSTOM_VARIABLE_DOB, dto.getDob());
//    tracker.variable(6, PiwikConstant.CUSTOM_VARIABLE_CARDNO, dto.getCard_no());
//    tracker.variable(7, PiwikConstant.CUSTOM_VARIABLE_AVAILABLE_LIMIT, dto.getAvailable_limit());
//    tracker.variable(8, PiwikConstant.CUSTOM_VARIABLE_REPAYMANT_LOAN_AMOUNT, dto.getLoan_amt());
//    tracker.variable(9, PiwikConstant.CUSTOM_VARIABLE_EXPIRY_DATE, dto.getExpiry_date());
//    tracker.variable(10, PiwikConstant.CUSTOM_VARIABLE_NAME, dto.getName());
//    tracker.variable(11, PiwikConstant.CUSTOM_VARIABLE_CARD_STATUS, dto.getCard_status());
//    tracker.variable(12, PiwikConstant.CUSTOM_VARIABLE_SERACH_KEY, dto.getSearch_key());
//    tracker.variable(13, PiwikConstant.CUSTOM_VARIABLE_PRODUCT_CATEGORY, dto.getProduct_category());
//    tracker.variable(14, PiwikConstant.CUSTOM_VARIABLE_UNIQUE_CODE, dto.getUnique_code());
//    tracker.variable(15, PiwikConstant.CUSTOM_VARIABLE_REF_NO, dto.getRef_no());
//    tracker.variable(16, PiwikConstant.CUSTOM_VARIABLE_EMI_SCHEDULE, dto.getEmi_schedule());
//    tracker.variable(17, PiwikConstant.CUSTOM_VARIABLE_EMI_STATEMANT, dto.getEmi_statement());
//    tracker.variable(18, PiwikConstant.CUSTOM_VARIABLE_PAY_HISTORY, dto.getPay_history());
//    tracker.variable(19, PiwikConstant.CUSTOM_VARIABLE_PAY_NOW, dto.getPay_now());
//    tracker.variable(20, PiwikConstant.CUSTOM_VARIABLE_CALL_BAJAJ, dto.getCall_bajaj());
//    tracker.variable(21, PiwikConstant.CUSTOM_VARIABLE_SEND_SMS, dto.getSend_sms());
//    tracker.variable(22, PiwikConstant.CUSTOM_VARIABLE_EMAIL, dto.getEmail());
//    tracker.variable(23, PiwikConstant.CUSTOM_VARIABLE_MOB_NO, dto.getSecondary_mob_no());
    return tracker;
  }

  private void startTracker(String url, int applicationId, String userId) {
    try {
      piwikTracker = Piwik.getInstance(this.cordova.getActivity().getApplication()).newTracker(url, applicationId);;

      piwikTracker.setUserId(userId);
	  piwikTracker.setDispatchInterval(-1);
      piwikTracker.dispatch();

    } catch (MalformedURLException e) {

    }
  }
}
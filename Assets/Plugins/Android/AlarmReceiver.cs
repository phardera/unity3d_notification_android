using UnityEngine;
using System.Collections;

public class AlarmReceiver : MonoBehaviour {

	// Use this for initialization
	void Start () {

	}
	
	// Update is called once per frame
	void Update () {
	
	}

  AndroidJavaObject nativeObj =null;
  void OnGUI(){
    if (GUI.Button(new Rect(Screen.width*0.5f-90.0f, 100.0f, 180.0f, 100.0f), "Create Notification")){
      if (nativeObj ==null)
        nativeObj =new AndroidJavaObject("com.macaronics.notification.AlarmReceiver");

      nativeObj.CallStatic("startAlarm", new object[4]{"THIS IS NAME", "THIS IS TITLE", "THIS IS LABEL", 10});
    }
  }
}

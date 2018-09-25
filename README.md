# Grin Scooters Blueooth

Sample app to demonstrate nearby bluetooth devices. Direct APK download [here](https://www.dropbox.com/s/vc9eppxp9pcgy4y/app-debug.apk)).

It uses kotlin & the following libraries:

- Retrofit: https://square.github.io/retrofit/
- RxKotlin: https://github.com/ReactiveX/RxKotlin
- RxAndroid: https://github.com/ReactiveX/RxAndroid
- Dagger: https://google.github.io/dagger/android.html

### How the app works?
- It responds to the "pull-to-refresh" gesture on all screens
- The first screen verifies the location permission (required for bluetooth scanning) and the bluetooth availability
- Then, starts bluetooth device scanning
- Once devices are available, there's an "add" button to the right, it sends the device info to an API in this format:
	```
	POST /add HTTP/1.1
	Host: grin-bluetooth-api.herokuapp.com
	Content-Type: application/json

	{
	   "name":"bluetooth device",
	   "address": "4567",
	   "strength":"-20db"
	}
	```
 - If the device already exist, it gets replaced. They are uniquely identified by the bluetooth MAC address.

![Grin Scooters Bluetooth Scan](https://raw.githubusercontent.com/lalongooo/grin-bluetooth/master/screenshots/screenshot1.png)

- The second screen makes a GET request in the following format:
    ```
    GET /devices HTTP/1.1
    Host: grin-bluetooth-api.herokuapp.com
    ```
- Then, display the devices from the API. The API response has the following format:

    ```
  [
    {
      "_id":"5ba9bc0475d3390015d3d46a",
      "name":"Jorge’s MacBook Pro",
      "address":"6C:96:CF:DF:51:F8",
      "strength":"-56",
      "created_at":"2018-09-25T04:46:05.181Z"
    },
    {
      "_id":"5ba9bc0c75d3390015d3d46b",
      "name":"SoundBuds Slim",
      "address":"20:9B:A5:6B:2A:9A",
      "strength":"-47",
      "created_at":"2018-09-25T04:40:05.588Z"
    }
  ]
    ```
- Sorting options are available: Sort by name and creation date

![Grin Scooters Bluetooth Saved Devices](https://raw.githubusercontent.com/lalongooo/grin-bluetooth/master/screenshots/screenshot2.png)

### micro-rfid-service

Le service qui nous permet de scanner une carte de voyageur et voir si c'est bien possible d'effectuer une transaction ou non. Voici une partie du code et des screenshot:

Cette photo montre que la carte n'est plus valide.

![image](https://user-images.githubusercontent.com/33737302/154197428-efb9b837-9c69-4812-9f0b-f3aa3355ed84.png)

Grâce à postman, on peut envoyer une demande de reservation ce qui va engendrer cette action de verification.

![image](https://user-images.githubusercontent.com/33737302/154197522-c101b599-058d-46d9-9742-8b181583611f.png)

Une autre autre:

![image](https://user-images.githubusercontent.com/33737302/154197609-988dad87-4908-4c59-8c43-e9e171827a12.png)

Voici un extrait du code du contrôlleur:

```java
@RestController
@RequestMapping("/rfid-api")
public class RFIDController implements SerialPortDataListener {

	@Autowired
	RFIDRepository rfidRepository;

	RFID rfid = new RFID();

	private static String bufferReadToString = ""; // empty, but not null
	private static int cutoffASCII = 10; // ASCII code of the character used for cut-off between received messages

	@Override
	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; // returns data on the Serial Port
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		byte[] buffer = new byte[event.getSerialPort().bytesAvailable()];
		event.getSerialPort().readBytes(buffer, buffer.length);

		String s = new String(buffer);
		bufferReadToString = bufferReadToString.concat(s); // converts the bytes read from the Serial port to a string
```

Invocation du service:

```java
	@GetMapping("/rfids/{passengerid}")
	public void readRFID(@PathVariable("passengerid") long passengerid) {

		rfid = rfidRepository.findByPassengerId(passengerid);

		if (rfid == null) {
			return;
		} else {

			SerialPort sp = SerialPort.getCommPort("/dev/ttyACM0"); // device name
			sp.setComPortParameters(9600, 8, 1, 0); // 9600,8,1,0default connection settings for Arduino
			sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

			if (sp.openPort()) {
				System.out.println("\n __Input RFID card now..\n");
				// PacketListener listenerObject = new PacketListener(); // creates new listener
				// object
				sp.addDataListener(this);
			}
```

### micro-auth-service

Le passager ou l'admin peuvent s'authentifier et par la suite un passager peut verifier son compte ou renouveler son abonnement.

### Usage

## Help

## Authors
Tarik Haroun<br/>

## Version History

* 0.1
    * Various bug fixes and optimizations
    * See [commit change]() or See [release history]()
    * Initial Release

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)

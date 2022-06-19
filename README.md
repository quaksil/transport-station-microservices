## Project ArchLog22
*Application basée sur les micro-services pour la gestion automatique du transport des passagers de la gare routière.*

### ABSTRACT

Il existe de nos jours des centaines de façons innovantes pour réaliser des applications d'une architecture orientée `micro-services`,
que ce soit les outils de développement, les frameworks, les outils de déploiement ou bien même les patrons de conception.

### Résumé

Parmis les scnéarios proposés dans l'ennoncé, nous nous sommes consacrés à l'aspect applicatif de l'application, dans chaque micro service, on donne importance à un seul aspect, comme vous pourrez le voir par la suite c.à.d exploiter et manipuler tout les outils avec quoi on avait à utiliser pour les maîtriser et bien comprendre le fonctionnement de cette architecture.

On aura principalement trois (03) propres micro services et trois (03) autres pour le déploiement dont Eureka Server, ApiGatway et Spring Cloud Config.

Les trois premiers concernent:

- <b>L'administrateur (micro-admin-service)</b>: Il gère les comptes utilisateurs (les passagers), assigne les cartes de abonnées une fois qu'il ont fait une commande (online ou au niveau de l'agence).

Pour l'administrateur, l'api qu'on a developpé on se focalise sur une application `CRUD REST API` avec Spring Boot pour le back-end. On fait le test des differetes requêtes (GET, POST, DELETE..) grâce à Postman. L'aspect front-end, on a pensé à le réaliser avec AngularJS, ce qui nous permet de deployer l'application Angular étant comme un microservice à part entière.

Ce qui fait l'objet de notre exploitation à ces outils c'est qu'ils ont une relation `dynamique`, AngularJS dans une architecture MVC sert d' `Observer` (Le modèle), et le controlleur REST de `View`, aussi, la relation entre Angular et la vue IHM est une relation de Model-View. Dans le premier cas, selon les types de requêtes et leurs routes entre les pages et les controlleurs Angular met à jour son état à chaque fois. d'où on parle du patron `Observer`.

- <b> Authentification et inscription (micro-auth-service)</b>: Ce micro service est exploié par tout utilisateur de la plateforme pour s'authentifier ou créer un nouveau compte.

- <b> RFID (micro-rfid-service)</b>: Ce micro service est exploité par un utilisateur (passager) possedant une carte RFID valide pour effectuer un voyage à chaque fois qu'il valide sa demande de voyage, le micro service se reveille et se met en attente jusqu'à reception du signal, si la carte est valide un voyage est soustrait de son abonnement et il est notifié.

Voilà en résumé, en ce que consiste notre travail accompagné du discovery des micro services avec Eureka, l'ApiGateway qui gère les redirections vers le bon microservice ainsi que Spring Cloud Config.

   ![image](https://user-images.githubusercontent.com/33737302/154192309-0b8b75e2-a745-4d56-ad8d-cb40160b9923.png)


## Use Case

La figure représente le diagramme de cas d’utilisation globale du système en temps normal, comme un passager peut toujours faire ses abonnement dans une agence ou déposer un dossier.

![image](https://user-images.githubusercontent.com/33737302/154191816-31913f39-211f-46fe-b5c3-c20a9629156b.png)

Les cas d’utilisations qui peuvent être envisagés: 

- S’authentifier [Agent, Passager] un cas primaire et il faut avoir déjà un compte. Il permet à l’utilisateur de s’authentifier et assurer l’accès personnel.

- Faire des demandes d'abonnement recharge d'abonnement (passagers).

- Consulter l’historique des voyages.

- Effectuer une transaction grâce à la carte RFID.

Plus en détail, un administrateur à déjà le dossier (informations) du passager, l'intègre dans le système si bien valable, offre la carte d'abonnement au passagers selon leurs âges (pour une remise), donc c'est l'administrateur qui gère ces utilisateurs. La carte contient un nombre fini de transactions.

Ensuite, selon le developpement des évenements, plusieurs scénarios peuvent arriver et c'est là que l'administrateur peut blocker ou débloquer la carte.

### Patrons de Conception

- *Comme on l'a déjà mentionné plus haut, la relation entre le REST Controller et AngularJS est dynamique ou chacun est séparé de l'autre (back-end, front-end), on peut démarrer une instance du front-end et se mettre tout le temps à observer les requêtes.*

- Puis, on a ceux proposés par spring, dont spring MVC, spring Cloud.

- L'api gateway (proxy).

En plus du diagramme de classe de base: 

![image](https://user-images.githubusercontent.com/33737302/154200088-4643a621-5531-4905-8e11-03173a95579d.png)


### Layout du projet

Comme vous pouvez le voir sur la photo, on a crée un projet `PARENT` qui contient tout les autres micro services, il s'agit d'un meilleur moyen de gestion.
**Aussi, les modules contenus dans le projet parent héritent de ses dépendences [pom.xml]*


![image](https://user-images.githubusercontent.com/33737302/154194201-d8cc674e-b69a-4db4-b021-9cb5168159d3.png)

On l'a déjà aussi précisé plus haut, donc on va procéder à étaler le projet selon ce qu'on a fait:

- Tout d'abord, Eureka Server, Api Gateway et SpringCloud Config sont à deployer (bien qu'on peut déployer les microservices avant la gateway)

![image](https://user-images.githubusercontent.com/33737302/154195795-8c4d5651-4ca7-4827-8eb7-d0e93a324790.png)

- Grâce à dev-tools, on peut mieux gérer nos services et faire des modifications **dynamiquement** en temps réél sans redémarrer le serveur http.

![image](https://user-images.githubusercontent.com/33737302/154195580-ae281043-a2bc-465e-b134-75a3cb545242.png)

### micro-admin-service
Ce service offre les opérations CRUD avec REST API en utilisant donc, le RestController voici un extrait de code des requêtes et réponses HTTP:

```java
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/passenger-api")
public class PassengerController {

	@Autowired
	PassengerRepository passengerRepository;

	@GetMapping("/passengers")
	public ResponseEntity<List<Passenger>> getAllPassengers(@RequestParam(required = false) String username) {
		try {
			List<Passenger> passengers = new ArrayList<Passenger>();
			if (username == null)
				passengerRepository.findAll().forEach(passengers::add);
			else
				passengerRepository.findByUsername(username).forEach(passengers::add);
			if (passengers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(passengers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/passengers/{id}")
	public ResponseEntity<Passenger> getPassengerById(@PathVariable("id") long id) {
		Optional<Passenger> passengerData = passengerRepository.findById(id);
		if (passengerData.isPresent()) {
			return new ResponseEntity<>(passengerData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/passengers")
	public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
		try {
			Passenger _passenger = passengerRepository.save(new Passenger(passenger.getFirstname(),
					passenger.getLastname(), passenger.getAge(), passenger.getAddress(), passenger.getOccupation(),
					passenger.getUsername(), passenger.getPassword()));
			return new ResponseEntity<>(_passenger, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/passengers/{id}")
	public ResponseEntity<Passenger> updatePassenger(@PathVariable("id") long id, @RequestBody Passenger passenger) {
		Optional<Passenger> passengerData = passengerRepository.findById(id);
		if (passengerData.isPresent()) {
			Passenger _passenger = passengerData.get();
			_passenger.setFirstname(passenger.getFirstname());
			_passenger.setLastname(passenger.getLastname());
			_passenger.setAge(passenger.getAge());
			_passenger.setAddress(passenger.getAddress());
			_passenger.setOccupation(passenger.getOccupation());
			_passenger.setUsername(passenger.getUsername());
			_passenger.setPassword(passenger.getPassword());

			return new ResponseEntity<>(passengerRepository.save(_passenger), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/passengers/{id}")
	public ResponseEntity<HttpStatus> deletePassenger(@PathVariable("id") long id) {
		try {
			passengerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/passengers")
	public ResponseEntity<HttpStatus> deleteAllPassengers() {
		try {
			passengerRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
   ```
   
   Voici l'entité passer:
   
   ```java
   @Entity
   @Table(name = "passengers")

   public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "age")
	private int age;

	@Column(name = "address")
	private String address;

	@Column(name = "occupation")
	private String occupation;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;	

	public Passenger() {

	}

    	public Passenger(String firstname, String lastname, int age, String address, String occupation, String username,
			String password) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.address = address;
		this.occupation = occupation;
		this.username = username;
		this.password = password;

	}
...
```

- Maintenant avec Postman, on peut quelques requêtes et on fait attention à la requête/réponse:

La méthode POST sur le lien `/passenger-api/passengers` nous crée un nouveau compte utilisateur (Réponse 201 est OK)

![image](https://user-images.githubusercontent.com/33737302/154196453-df094f2f-16be-492e-ac7f-b4c08843552e.png)

Ainsi de suite pour les autres, il faut noter aussi que ceci est une REST API.

On peut lancer le front avec cette commande: ![image](https://user-images.githubusercontent.com/33737302/154198360-26651c9f-c897-4df9-9881-08a0f3b3e384.png)

sur localhost:8081

![image](https://user-images.githubusercontent.com/33737302/154198619-22e3b3ea-c9ca-43b7-bfbf-c9149235f1ee.png)

![image](https://user-images.githubusercontent.com/33737302/154198579-ce560f7e-981d-4f2b-9ee2-32ad204f7da9.png)


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

## Conclusion

Grâce à ce travail de tp, on a pu enrichir nos connaissances sur un bon nombre de choses et surtout l'utilité des microservices dans un milieu imprédictible, étant donné qu'on a pas entammé la totalié de ce tp, ça reste très interessant et ça nous motive emplement pour travailler sur les micro services.

### Usage

## Help

## Authors
Tarik Haroun<br/>
Email: tarik.haroun@univ-constantine2.dz 

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

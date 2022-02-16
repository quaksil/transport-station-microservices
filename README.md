## Project Architecture Logicielle 2022<br/>

--> Application basée sur les micro-services pour la gestion automatique du transport des passagers de la gare routière.

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

- </b> Authentification et inscription (micro-auth-service)</b>: Ce micro service est exploié par tout utilisateur de la plateforme pour s'authentifier ou créer un nouveau compte.

- </b> RFID (micro-rfid-service)</b>: Ce micro service est exploité par un utilisateur (passager) possedant une carte RFID valide pour effectuer un voyage à chaque fois qu'il valide sa demande de voyage, le micro service se reveille et se met en attente jusqu'à reception du signal, si la carte est valide un voyage est soustrait de son abonnement et il est notifié.

Voilà en résumé, en ce que consiste notre travail accompagné du discovery des micro services avec Eureka, l'ApiGateway qui gère les redirections vers le bon microservice ainsi que Spring Cloud Config.


## Use Case




### Consumer Agent


## Client-Server architecture

We can split this application to a client-server application by simply running the buyer and the seller agents on a host main container and the client (consumer) on a another machine as a client, once logged in, the consumer agent will be deployed on the remote host main container.

### Multiple JADE platforms and containers

### Usage

### Executing program

## Help

## Authors
Tarik Haroun<br/>
Gmail: tarik.haroun@univ-constantine2.dz 

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

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

### Layout du projet

Comme vous pouvez le voir sur la photo, on a crée un projet `PARENT` qui contient tout les autres micro services, il s'agit d'un meilleur moyen de gestion.
**Aussi, les modules contenus dans le projet parent héritent de ses dépendences [pom.xml]*


![image](https://user-images.githubusercontent.com/33737302/154194201-d8cc674e-b69a-4db4-b021-9cb5168159d3.png)

On l'a déjà aussi précisé plus haut, donc on va procéder à étaler le projet selon ce qu'on a fait:

- Tout d'abord, Eureka Server, Api Gateway et SpringCloud Config sont à deployer (bien qu'on peut déployer les microservices avant la gateway)

- Grâce à dev-tools, on peut mieux gérer nos services et faire des modifications **dynamiquement** sans redémarrer le serveur http.
- 
![image](https://user-images.githubusercontent.com/33737302/154195037-2accb112-bf24-4a06-a7db-a0dfbf3d7ac3.png)

![image](https://user-images.githubusercontent.com/33737302/154195464-2330bb7e-e0e1-44dd-bff0-d7eb5de53b1d.png)



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

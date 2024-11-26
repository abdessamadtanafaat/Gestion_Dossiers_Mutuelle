Explication de chaque classe dans code Batch
1. BatchConfig
La classe BatchConfig est la configuration principale de votre traitement par lot (batch). Elle définit le processus global et organise les différents composants nécessaires au traitement des dossiers.

CompositeItemProcessor : Un processeur qui enchaîne plusieurs processeurs (comme la validation, le remboursement de la consultation, le traitement des médicaments, etc.). Chaque processeur s'exécute dans l'ordre que vous avez défini. Il combine les résultats de plusieurs étapes de traitement en un seul objet de sortie.
Step : Un pas dans un job. Ce pas définit comment un ensemble d'éléments (ici des dossiers) va être lu, traité, puis écrit dans la base de données. Vous configurez ici le lecteur, le processeur, et l'écrivain des données.
Job : Un job représente l'ensemble des étapes. Ici, vous avez un job qui consiste en une seule étape de traitement (par exemple, lire les dossiers, appliquer les processeurs et écrire dans la base de données).
DossierSkipPolicy, DossierRetryListener, DossierSkipListener, LoggingRetryListener : Ces classes gèrent la gestion des erreurs et des exceptions pendant le traitement des dossiers, en fonction de certaines conditions. Elles sont associées aux étapes du traitement pour surveiller et réagir en cas de problèmes.
2. DossierSkipPolicy
La classe DossierSkipPolicy définit une logique pour décider si un dossier doit être ignoré (sauté) lorsqu'une erreur est rencontrée. Elle implémente l'interface SkipPolicy.

shouldSkip() : Cette méthode est appelée lorsqu'une exception est levée. Si l'exception est une IllegalArgumentException et que le compteur de sauts (skipCount) est inférieur à 5, le dossier est ignoré, sinon l'exception est considérée comme non gérable et l'exécution échoue.
3. DossierRetryListener
La classe DossierRetryListener écoute les événements de tentative de reprise après une erreur.

onError() : Lorsqu'une erreur se produit dans une tentative de reprise, cette méthode est appelée. Elle enregistre un message d'erreur indiquant quel numéro de tentative a échoué et quel était le message d'erreur.
4. DossierSkipListener
La classe DossierSkipListener est un écouteur qui est invoqué lorsqu'un dossier est ignoré (skip).

onSkipInRead() : Cette méthode est appelée lorsqu'un dossier est ignoré pendant la lecture (par exemple, s'il y a un problème de formatage du fichier ou des données manquantes).
onSkipInWrite() : Lorsqu'un dossier échoue pendant l'écriture (en raison d'une exception dans la base de données, par exemple), cette méthode est appelée.
onSkipInProcess() : Si une erreur se produit pendant le traitement d'un dossier (par exemple, lors de la validation ou de l'application des règles de remboursement), cette méthode est appelée.
5. LoggingRetryListener
La classe LoggingRetryListener est similaire à DossierRetryListener mais se concentre sur l'enregistrement des tentatives de reprise.

onError() : Cette méthode enregistre un message indiquant une tentative de reprise échouée, en affichant le numéro de la tentative et l'erreur rencontrée.
En résumé :
BatchConfig : Configure le flux principal de traitement des dossiers, avec des étapes et des processeurs pour chaque partie du traitement (validation, calcul des remboursements, etc.).
DossierSkipPolicy : Définit les règles pour ignorer les dossiers en cas d'erreurs spécifiques.
DossierRetryListener : Surveille et logge les erreurs de reprise de traitement des dossiers.
DossierSkipListener : Surveille et logge les erreurs lorsque des dossiers sont ignorés pendant les différentes étapes (lecture, traitement, écriture).
LoggingRetryListener : Surveille les tentatives de reprise, mais se concentre principalement sur l'enregistrement de ces tentatives.
Ces composants permettent de gérer efficacement les erreurs et d'assurer la bonne gestion des dossiers en cas de problèmes pendant les traitements par lot. Vous pouvez ajuster les règles de reprise et de saut en fonction des besoins de votre processus de gestion des dossiers.


1. ConsultationProcessor
Cette classe est responsable du calcul du remboursement de la consultation dans un dossier. Elle applique un pourcentage fixe (80%) sur le prix de la consultation et l'ajoute au montant total des frais.

process() :
Affiche les valeurs initiales (montant total des frais et prix de la consultation).
Calcule le remboursement de la consultation en appliquant un pourcentage (ici 80%).
Ajoute ce remboursement au montant total des frais du dossier.
Affiche le montant total des frais après ajout du remboursement de la consultation.
Renvoie le dossier avec les montants mis à jour.
2. TotalRemboursementProcessor
Cette classe calcule le montant total des remboursements, incluant les remboursements des traitements et de la consultation. Elle met à jour le montant total des frais du dossier avec le total des remboursements.

process() :
Prend le montant total des frais calculé dans les processeurs précédents.
Si nécessaire, il est possible de recalculer le remboursement total.
Retourne le dossier avec le montant des frais mis à jour.
3. TraitementMappingProcessor
Cette classe est responsable de l'association des traitements dans un dossier avec les médicaments référencés dans la base de données. Elle met à jour les informations de chaque traitement en fonction des données du référentiel des médicaments.

process() :
Parcourt les traitements du dossier et vérifie si le médicament est référencé dans la base de données.
Si le médicament existe, il met à jour les informations du traitement, comme le nom du médicament, son type, son prix et le pourcentage de remboursement.
Si le médicament n'est pas référencé, une exception est levée.
4. TraitementRemboursementProcessor
Cette classe calcule le remboursement pour chaque médicament dans le dossier, en fonction de son prix de référence et du pourcentage de remboursement dans le référentiel des médicaments. Elle met à jour le montant total des frais du dossier en ajoutant le remboursement de chaque traitement.

process() :
Calcule le remboursement de chaque traitement en fonction du prix de référence et du pourcentage de remboursement du médicament référencé.
Additionne ces remboursements au montant total des frais du dossier.
Calcule le montant total remboursé comme la différence entre le montant final des frais et le montant initial des frais.
Met à jour le dossier avec le montant total des frais et le montant total remboursé.
5. ValidationProcessor
Cette classe valide les données du dossier avant qu'elles ne soient traitées. Elle vérifie que tous les champs nécessaires sont remplis et que les traitements sont référencés dans la base de données.

process() :
Vérifie que le nom de l'assuré, le numéro d'affiliation et le prix de la consultation sont présents et valides.
Vérifie que la liste des traitements est présente et non vide.
Pour chaque traitement, vérifie que le médicament référencé existe dans la base de données.
Si des erreurs sont trouvées, une exception est levée et le dossier est rejeté.
6. DossierDatabaseWriter
Cette classe est responsable de l'écriture des dossiers traités dans la base de données. Elle enregistre tous les dossiers traités à l'aide du repository DossierRepository.

write() :
Enregistre tous les dossiers présents dans le Chunk (lot d'éléments traités) dans la base de données en utilisant le repository DossierRepository.
Affiche un message dans le journal pour chaque dossier enregistré.
En résumé :
ConsultationProcessor : Calcule le remboursement de la consultation et met à jour le montant total des frais du dossier.
TotalRemboursementProcessor : Met à jour le montant total des frais avec le remboursement total (consultation + traitements).
TraitementMappingProcessor : Associe chaque traitement dans le dossier aux médicaments référencés dans la base de données et met à jour les informations du traitement.
TraitementRemboursementProcessor : Calcule le remboursement de chaque médicament référencé et met à jour le montant total des frais du dossier, ainsi que le montant total remboursé.
ValidationProcessor : Vérifie la validité des données du dossier et assure que tous les traitements sont référencés dans la base de données avant de les accepter pour le traitement.
DossierDatabaseWriter : Sauvegarde les dossiers traités dans la base de données.
Ces classes sont utilisées dans le cadre du traitement par lot, où les dossiers sont lus, validés, traités (avec des calculs de remboursement) et ensuite enregistrés dans la base de données.




Configuration Spring Batch
La configuration Spring Batch que vous avez partagée influence le comportement du traitement des jobs par Spring Batch. Voici une explication de chaque propriété :

spring.batch.job.enabled=true
Cela active l'exécution des jobs Spring Batch dès le démarrage de l'application. Si cette propriété est définie sur false, les jobs ne seront pas lancés automatiquement au démarrage de l'application, mais vous pourrez les déclencher manuellement via une interface ou un autre mécanisme.

spring.batch.jdbc.initialize-schema=always
Cette propriété indique que Spring Batch doit gérer la création (et, si nécessaire, la mise à jour) des tables nécessaires pour exécuter des jobs batch dans la base de données. Le paramètre always garantit que Spring Batch recréera les schémas de la base de données à chaque démarrage de l'application. Cela est particulièrement utile pendant le développement, mais il peut être modifié en fonction des besoins de votre environnement de production (par exemple, en never ou embedded).

logging.level.org.springframework.batch=DEBUG
Cette configuration active le niveau de log DEBUG pour toutes les classes sous le package org.springframework.batch. Cela permet d'obtenir des informations détaillées sur le traitement des jobs, ce qui peut être utile pour le débogage ou la surveillance des jobs batch. Vous pourrez voir chaque étape du traitement des données dans le log.

logging.level.org.springframework.retry=DEBUG
Active également le niveau de log DEBUG pour les classes liées au mécanisme de retry de Spring. Spring Batch peut essayer de réexécuter des étapes en cas d'échec (par exemple, pour les erreurs temporaires comme des problèmes de réseau), et cette configuration vous permet de suivre les tentatives de retry dans les logs.

Le rôle des tables Batch
Spring Batch utilise plusieurs tables dans la base de données pour suivre l'exécution des jobs, des étapes et des contextes d'exécution. Ces tables sont essentielles pour la gestion des jobs batch et le suivi des tâches en cours ou échouées. Voici les principales tables utilisées par Spring Batch et leur rôle :

1. BATCH_JOB_EXECUTION
Cette table contient des informations sur l'exécution des jobs eux-mêmes.
Chaque ligne représente une exécution d'un job, avec des informations telles que le statut de l'exécution, le moment où elle a commencé et terminé, et éventuellement un message d'erreur ou un code de statut.
Les colonnes typiques de cette table incluent :
JOB_EXECUTION_ID : Un identifiant unique pour chaque exécution de job.
JOB_INSTANCE_ID : L'ID de l'instance du job, identifiant un job spécifique.
STATUS : Le statut de l'exécution (ex : COMPLETED, FAILED).
START_TIME et END_TIME : Les horaires de début et de fin de l'exécution du job.
EXIT_CODE : Le code de sortie du job.
EXIT_MESSAGE : Un message associé à l'exécution du job (par exemple, message d'erreur en cas d'échec).
2. BATCH_JOB_INSTANCE
Cette table garde une trace de chaque instance de job qui a été exécutée.
Chaque instance de job est un "type de job", par exemple, un job spécifique que vous définissez dans votre application (par exemple, processDossierJob).
Les colonnes typiques de cette table incluent :
JOB_INSTANCE_ID : L'ID unique de l'instance de job.
JOB_NAME : Le nom du job (par exemple, processDossierJob).
JOB_PARAMETERS : Les paramètres associés à l'instance du job. Ces paramètres peuvent être utilisés pour personnaliser l'exécution du job.
3. BATCH_JOB_EXECUTION_CONTEXT
Cette table contient des informations contextuelles supplémentaires pour chaque exécution de job, telles que des informations d'état spécifiques, des variables, ou d'autres données de processus à persister au-delà de l'exécution d'une étape.
Elle permet de stocker des données entre les étapes d'un job ou d'une exécution de job. Ces données sont associées à l'exécution de job dans la table BATCH_JOB_EXECUTION.
Les colonnes typiques de cette table incluent :
JOB_EXECUTION_ID : L'ID de l'exécution de job correspondante.
SHORT_CONTEXT : Un résumé du contexte (ex : paramètres des étapes, état des traitements).
SERIALIZED_CONTEXT : Les données du contexte sérialisées (en JSON ou autre format).
4. BATCH_STEP_EXECUTION
Cette table garde une trace de l'exécution de chaque étape individuelle dans un job.
Chaque ligne représente l'exécution d'une étape dans un job. Chaque job peut avoir plusieurs étapes, et chacune est enregistrée séparément dans cette table.
Les colonnes typiques de cette table incluent :
STEP_EXECUTION_ID : L'ID unique de l'exécution de l'étape.
JOB_EXECUTION_ID : L'ID de l'exécution de job correspondante.
STEP_NAME : Le nom de l'étape (par exemple, processStep).
STATUS : Le statut de l'exécution de l'étape (ex : COMPLETED, FAILED).
START_TIME et END_TIME : Les horaires de début et de fin de l'exécution de l'étape.
EXIT_CODE et EXIT_MESSAGE : Le code de sortie et le message associé à l'exécution de l'étape.
5. BATCH_STEP_EXECUTION_CONTEXT
Cette table est similaire à BATCH_JOB_EXECUTION_CONTEXT, mais elle est utilisée pour stocker des informations contextuelles pour les étapes spécifiques d'un job.
Elle permet de persister des données spécifiques à une étape, permettant ainsi la reprise ou le suivi des données pendant l'exécution des étapes.
Les colonnes typiques sont similaires à celles de BATCH_JOB_EXECUTION_CONTEXT, mais elles sont liées aux étapes et non aux jobs dans leur ensemble.
Résumé des rôles des tables :
BATCH_JOB_EXECUTION : Suivi des exécutions de job.
BATCH_JOB_INSTANCE : Suivi des instances de jobs.
BATCH_JOB_EXECUTION_CONTEXT : Contexte spécifique pour chaque exécution de job.
BATCH_STEP_EXECUTION : Suivi des exécutions de chaque étape.
BATCH_STEP_EXECUTION_CONTEXT : Contexte spécifique pour chaque exécution d'étape.
Ces tables permettent à Spring Batch de gérer le processus d'exécution des jobs de manière robuste et de suivre leur état, de permettre les reprises en cas d'échec et d'assurer une gestion efficace des erreurs et des exceptions.


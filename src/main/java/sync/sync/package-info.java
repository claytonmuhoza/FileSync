/**
 * Ce package contient les composants responsables de l'exécution de la synchronisation.
 *
 * <p>Il regroupe :</p>
 * <ul>
 *   <li>{@link sync.sync.SyncVisitor} — Visiteur chargé d'appliquer les règles de synchronisation
 *       (copie, suppression, détection de conflits) sur les fichiers et dossiers.</li>
 *   <li>{@link sync.sync.SyncService} — Interface définissant le service de synchronisation</li>
 *   <li>{@link sync.sync.SyncServiceStd} — Implémentation standard de {@code SyncService}</li>
 * </ul>
 *
 * <p>Ce package constitue la logique centrale de l'application. Il dépend des packages :</p>
 * <ul>
 *   <li>{@link sync.fs} pour la manipulation des fichiers abstraits</li>
 *   <li>{@link sync.profile} pour les informations de configuration</li>
 *   <li>{@link sync.registry} pour l’historique de synchronisation</li>
 * </ul>
 *
 * @see sync.sync.SyncVisitor
 * @see sync.sync.SyncService
 * @see sync.profile.Profile
 */
package sync.sync;
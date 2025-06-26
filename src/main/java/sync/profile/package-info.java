/**
 * Ce package gère les profils de synchronisation.
 *
 * <p>Un {@link sync.profile.Profile} encapsule :</p>
 * <ul>
 *   <li>Un nom unique représenté par {@link sync.profile.ProfileName}</li>
 *   <li>Deux chemins à synchroniser, de type {@link sync.fs.SyncPath}</li>
 *   <li>Un registre {@link sync.registry.Register} qui mémorise l’état des fichiers lors de la dernière synchronisation</li>
 * </ul>
 *
 * <p>Les classes associées permettent :</p>
 * <ul>
 *   <li>De construire un profil via {@link sync.profile.ProfileBuilder}</li>
 *   <li>De le sauvegarder via {@link sync.profile.ProfileSaver}</li>
 *   <li>De le charger via {@link sync.profile.ProfileLoader}</li>
 * </ul>
 *
 * <p>Ce package constitue l’élément central de configuration utilisé par le service de synchronisation.</p>
 */
package sync.profile;

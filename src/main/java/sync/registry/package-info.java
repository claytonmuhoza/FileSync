/**
 * Ce package contient les interfaces et classes liées à la gestion du registre de synchronisation.
 *
 * <p>Le registre mémorise l’état des fichiers et dossiers à la dernière synchronisation
 * afin de détecter les ajouts, suppressions et modifications futures.</p>
 *
 * <p>Principales interfaces :</p>
 * <ul>
 *   <li>{@link sync.registry.Register} — Accès et manipulation du registre</li>
 *   <li>{@link sync.registry.RegisterBuilder} — Construction d’un registre à partir d’un ensemble d’entrées</li>
 * </ul>
 *
 * <p>Ce package est utilisé par {@link sync.profile.Profile} et exploité par {@link sync.sync.SyncVisitor}.</p>
 */
package sync.registry;

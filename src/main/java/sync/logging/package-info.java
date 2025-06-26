/**
 * Ce package fournit des services de journalisation pour la synchronisation.
 *
 * <p>Il permet d'afficher les messages liés aux opérations de synchronisation,
 * tels que les copies, suppressions, conflits ou succès.</p>
 *
 * <p>Principales classes :</p>
 * <ul>
 *   <li>{@link sync.logging.SyncLogger} — Interface abstraite de journalisation</li>
 *   <li>{@link sync.logging.ConsoleLogger} — Implémentation concrète qui affiche les messages dans la console</li>
 * </ul>
 *
 * <p>Ce package est utilisé notamment par {@link sync.sync.SyncVisitor} pour tracer les actions exécutées.</p>
 */
package sync.logging;

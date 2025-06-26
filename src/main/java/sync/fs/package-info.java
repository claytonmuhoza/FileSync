/**
 * Ce package définit les abstractions du système de fichiers synchronisable.
 *
 * <p>Il repose sur une structure orientée objets permettant de modéliser :</p>
 * <ul>
 *   <li>Les entrées de fichiers et dossiers via {@link sync.fs.Entry}</li>
 *   <li>Le patron de conception Composite pour représenter les arborescences (fichiers/dossiers)</li>
 *   <li>Le patron Visiteur via {@link sync.fs.EntryVisitor} pour appliquer des opérations sur ces entrées</li>
 *   <li>Les chemins relatifs avec {@link sync.fs.RelativePath}</li>
 *   <li>Les systèmes de fichiers synchronisables (locaux, WebDAV…) via {@link sync.fs.SyncPath}</li>
 *   <li>Les opérations de lecture/écriture séparées avec {@link sync.fs.SyncPathExplorer} et {@link sync.fs.SyncPathOperator}</li>
 * </ul>
 *
 * <p>Les entrées sont créées à l’aide de {@link sync.fs.EntryFactory}, ce qui permet de désolidariser
 * leur construction du type de système de fichiers.</p>
 *
 * <p>Ce package est exploité notamment par {@link sync.sync.SyncVisitor} pour naviguer et synchroniser les fichiers.</p>
 */
package sync.fs;

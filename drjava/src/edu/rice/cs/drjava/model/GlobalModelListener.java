/*BEGIN_COPYRIGHT_BLOCK
 *
 * This file is a part of DrJava. Current versions of this project are available
 * at http://sourceforge.net/projects/drjava
 *
 * Copyright (C) 2001-2002 JavaPLT group at Rice University (javaplt@rice.edu)
 * 
 * DrJava is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * DrJava is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * or see http://www.gnu.org/licenses/gpl.html
 *
 * In addition, as a special exception, the JavaPLT group at Rice University
 * (javaplt@rice.edu) gives permission to link the code of DrJava with
 * the classes in the gj.util package, even if they are provided in binary-only
 * form, and distribute linked combinations including the DrJava and the
 * gj.util package. You must obey the GNU General Public License in all
 * respects for all of the code used other than these classes in the gj.util
 * package: Dictionary, HashtableEntry, ValueEnumerator, Enumeration,
 * KeyEnumerator, Vector, Hashtable, Stack, VectorEnumerator.
 *
 * If you modify this file, you may extend this exception to your version of the
 * file, but you are not obligated to do so. If you do not wish to
 * do so, delete this exception statement from your version. (However, the
 * present version of DrJava depends on these classes, so you'd want to
 * remove the dependency first!)
 *
END_COPYRIGHT_BLOCK*/

package edu.rice.cs.drjava.model;

import java.io.File;

/**
 * An interface for responding to events generated by the GlobalModel.
 *
 * @version $Id$
 */
public interface GlobalModelListener {
  /**
   * Reasons provided for demanding a save before proceeding.
   */
  public class SaveReason {
    private SaveReason() {}
  }

  /**
   * This enumeration of save reason means that we want to compile.
   */
  public static final SaveReason COMPILE_REASON = new SaveReason();

  /**
   * This enumeration of save reason means that we want to run JUnit.
   */
  public static final SaveReason JUNIT_REASON = new SaveReason();

  /**
   * This enumeration of save reason means that we want to debug with JSwat.
   */
  public static final SaveReason DEBUG_REASON = new SaveReason();
  
  /**
   * Called after a new document is created.
   */
  public void newFileCreated(OpenDefinitionsDocument doc);

  /**
   * Called after the current document is saved.
   */
  public void fileSaved(OpenDefinitionsDocument doc);

  /**
   * Called after a file is opened and read into the current document.
   */
  public void fileOpened(OpenDefinitionsDocument doc);

  /**
   * Called after a document is closed.
   */
  public void fileClosed(OpenDefinitionsDocument doc);

  /**
   * Called after a compile is started by the GlobalModel.
   */
  public void compileStarted();

  /**
   * Called when a compile has finished running.
   */
  public void compileEnded();

  /**
   * Called after JUnit is started by the GlobalModel.
   */
  public void junitStarted();

  /**
   * Called after JUnit is finished running tests.
   */
  public void junitEnded();

  /**
   * Called after an interaction is started by the GlobalModel.
   */
  public void interactionStarted();

  /**
   * Called when an interaction has finished running.
   */
  public void interactionEnded();

  /**
   * Called when the interactions window is reset.
   */
  public void interactionsReset();

  /**
   * Called when the interactions JVM was closed by System.exit
   * or by being aborted. Immediately after this the interactions
   * will be reset.
   *
   * @param status The exit code
   */
  public void interactionsExited(int status);

  /**
   * Called when the console window is reset.
   */
  public void consoleReset();

  /**
   * Called to demand that the listeners save the current document
   * before the GlobalModel can proceed with another action.  
   */
  public void saveBeforeProceeding(SaveReason reason);

  /**
   * Called to demand that the listeners save all open documents
   * before the GlobalModel can proceed with another action.  
   */
  public void saveAllBeforeProceeding(SaveReason reason);
  
  /**
   * Called when trying to test a non-TestCase class.
   */
  public void nonTestCase();

  /**
   * Called to ask the listener if it is OK to abandon the current
   * document.
   */
  public boolean canAbandonFile(OpenDefinitionsDocument doc);
}

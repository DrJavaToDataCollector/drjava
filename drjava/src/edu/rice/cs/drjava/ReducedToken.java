/* $Id$ */

package edu.rice.cs.drjava;

abstract class ReducedToken
{
	protected int _state;
	public static final int FREE = 0;
  public static final int INSIDE_QUOTE = 1;
  public static final int INSIDE_BLOCK_COMMENT = 2;
  public static final int INSIDE_LINE_COMMENT = 4;
	
	public abstract int getSize();
	public abstract String getType();
	public abstract void setType(String type);
	public abstract void flip();

	public int getState()
		{
			return _state;
		}

	public void setState(int state)
		{
			_state = state;
		}

  /**
   * Indicates whether this brace is shadowed.
   * Shadowing occurs when a brace has been swallowed by a
   * comment or an open quote.
   * @return true if the brace is shadowed.
   */ 
  public boolean isShadowed()
    {
      return _state != FREE;
    }

  /**
   * Indicates whether this brace is inside quotes.
   * @return true if the brace is inside quotes.
   */ 
  public boolean isQuoted()
    {
      return _state == INSIDE_QUOTE;
    }

  /**
   * Indicates whether this brace is commented out.
   * @return true if the brace is hidden by comments.
   */ 
  public boolean isCommented()
    {
      return isInBlockComment() || isInLineComment();
    }

  public boolean isInBlockComment()
    {
      return _state == INSIDE_BLOCK_COMMENT;
    }
    
  public boolean isInLineComment()
    {
      return _state == INSIDE_LINE_COMMENT;
    }

	public abstract boolean isMultipleCharBrace();

	public abstract boolean isGap();

	public abstract boolean isLineComment();

	public abstract boolean isBlockCommentStart();

	public abstract boolean isBlockCommentEnd();

	public abstract boolean isNewline();

	public abstract boolean isSlash();

	public abstract boolean isStar();

	public abstract boolean isQuote();

	public abstract void grow(int delta);

	public abstract void shrink(int delta);

	public abstract boolean isOpen();

	public abstract boolean isClosed();	
	
}

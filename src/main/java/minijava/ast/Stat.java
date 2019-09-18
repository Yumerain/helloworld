package minijava.ast;

import java.io.IOException;

import minijava.Env;
import minijava.ExprList;
import minijava.Location;
import minijava.Scope;
import minijava.TemplateException;
import minijava.Writer;

public abstract class Stat {
	
	protected Location location;
	
	public Stat setLocation(Location location) {
		this.location = location;
		return this;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public void setExprList(ExprList exprList) {
	}
	
	public void setStat(Stat stat) {
	}
	
	public abstract void exec(Env env, Scope scope, Writer writer);
	
	public boolean hasEnd() {
		return false;
	}
	
	protected void write(Writer writer, String str) {
		try {
			writer.write(str, 0, str.length());
		} catch (IOException e) {
			throw new TemplateException(e.getMessage(), location, e);
		}
	}
}



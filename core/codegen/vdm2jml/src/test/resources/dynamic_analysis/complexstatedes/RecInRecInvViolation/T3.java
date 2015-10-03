package project.Entrytypes;

import org.overture.codegen.runtime.*;

import java.util.*;


//@ nullable_by_default
@SuppressWarnings("all")
final public class T3 implements Record {
    public project.Entrytypes.T4 t4;

    //@ public instance invariant project.Entry.invChecksOn ==> inv_T3(t4);
    public T3(final project.Entrytypes.T4 _t4) {
        t4 = (_t4 != null) ? Utils.copy(_t4) : null;
    }

    /*@ pure @*/
    public boolean equals(final Object obj) {
        if (!(obj instanceof project.Entrytypes.T3)) {
            return false;
        }

        project.Entrytypes.T3 other = ((project.Entrytypes.T3) obj);

        return Utils.equals(t4, other.t4);
    }

    /*@ pure @*/
    public int hashCode() {
        return Utils.hashCode(t4);
    }

    /*@ pure @*/
    public project.Entrytypes.T3 copy() {
        return new project.Entrytypes.T3(t4);
    }

    /*@ pure @*/
    public String toString() {
        return "mk_Entry`T3" + Utils.formatFields(t4);
    }

    /*@ pure @*/
    public project.Entrytypes.T4 get_t4() {
        project.Entrytypes.T4 ret_5 = t4;

        //@ assert ret_5 != null;
        return ret_5;
    }

    public void set_t4(final project.Entrytypes.T4 _t4) {
        //@ assert _t4 != null;
        t4 = _t4;

        //@ assert t4 != null;
    }

    /*@ pure @*/
    public Boolean valid() {
        return true;
    }

    /*@ pure @*/
    /*@ helper @*/
    public static Boolean inv_T3(final project.Entrytypes.T4 _t4) {
        return _t4.x.longValue() > 3L;
    }
}
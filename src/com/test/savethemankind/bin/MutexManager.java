package com.test.savethemankind.bin;

import java.util.concurrent.Semaphore;

// TODO: May be rename this to MutexHashMap
// Singleton ?
public class MutexManager <TypeKey, TypeValue> extends AbstractMutexManager <TypeKey, TypeValue> {

    // sun.reflect.generics.reflectiveObjects.TypeVariableImpl cannot be cast to java.lang.Class
//    private static MutexManager instance = new MutexManager();

    // sun.reflect.generics.reflectiveObjects.TypeVariableImpl cannot be cast to java.lang.Class
//    public static MutexManager getInstance() { return instance; }


    public TypeValue getMutex(String threadType, TypeKey mutexPurpose) throws InterruptedException {
        // calculate threadId of the calling thread
        Long threadIdA = Thread.currentThread().getId();
        Long threadIdB = null;

        switch (threadType) {
            case "D":
                threadIdB = D_Thread.getInstance().getId();
                break;
            case "V":
                threadIdB = V_Thread.getInstance().getId();
                break;
            case "C":
                threadIdB = C_Thread.getInstance().getId();
                break;
            default:
                throw new InterruptedException(
                        "Error: threads of type " + threadType + " are not supported."
                );
        }

        TypeValue value12 = null;
        TypeValue value21 = null;

        String key12 = threadIdA.toString() + ":" + threadIdB.toString() + ":"
                + mutexPurpose.toString();
        String key21 = threadIdB.toString() + ":" + threadIdA.toString() + ":"
                + mutexPurpose.toString();

        value12 = super.obtain((TypeKey) (key12));
        value21 = super.obtain((TypeKey) (key21));

        if ((value12 != null) && (value21 != null) && (value12 != value21)) {
            Application.printMsg("hash[" + key12 + "]=" + value12);
            Application.printMsg("hash[" + key21 + "]=" + value21);
            throw new InterruptedException("Error: the hash is not symmetric.");
        }

        if (value12 != null) {
            return value12;
        } else if (value21 != null) {
            return value21;
        } else {
            // TODO: here concurrent creation is possible?
            //return super.insert((TypeKey)key12, (TypeValue)(super.TypeV.getConstructor(TypeV).));
            return super.insert((TypeKey)key12, (TypeValue)(new Semaphore(1)));
        }
    }
}

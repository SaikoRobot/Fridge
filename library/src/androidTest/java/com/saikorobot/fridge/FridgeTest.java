package com.saikorobot.fridge;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.test.runner.AndroidJUnit4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * FridgeTest.
 */
@RunWith(AndroidJUnit4.class)
public class FridgeTest {

    @Test
    public void testIntSaveRestore() throws Exception {
        IntegerTestObject testObject = new IntegerTestObject();
        testObject.intNumber = 100;
        testObject.intArrayNumber = new int[]{1, 2};
        testObject.intArrayListNumber = new ArrayList<>(Arrays.asList(10, 20));
        testObject.integerNumber = 9999;

        Bundle bundle = new Bundle();
        Fridge.saveInstanceState(testObject, bundle);
        IntegerTestObject restored = new IntegerTestObject();
        Fridge.restoreInstanceState(restored, bundle);

        assertEquals(0, restored.intNumberNoAnnotation);
        assertEquals(testObject.intNumber, restored.intNumber);
        assertNotNull(restored.intArrayNumber);
        assertArrayEquals(testObject.intArrayNumber, restored.intArrayNumber);
        assertNotNull(restored.intArrayListNumber);
        assertEquals(testObject.intArrayListNumber, restored.intArrayListNumber);
        assertNotNull(restored.integerNumber);
        assertEquals(testObject.integerNumber, restored.integerNumber);

        testObject.intNumber = 999;
        testObject.intArrayNumber = null;
        testObject.intArrayListNumber = null;
        testObject.integerNumber = null;

        bundle = new Bundle();
        Fridge.saveInstanceState(testObject, bundle);
        restored = new IntegerTestObject();
        Fridge.restoreInstanceState(restored, bundle);

        assertEquals(0, restored.intNumberNoAnnotation);
        assertEquals(testObject.intNumber, restored.intNumber);
        assertNull(restored.intArrayNumber);
        assertNull(restored.intArrayListNumber);
        assertNull(restored.integerNumber);
    }

    @Test
    public void testBooleanSaveRestore() throws Exception {
        BooleanTestObject testObject = new BooleanTestObject();
        testObject.boolValue = true;
        testObject.boolArrayValue = new boolean[]{true, false, true};
        testObject.booleanValue = true;

        Bundle bundle = new Bundle();
        Fridge.saveInstanceState(testObject, bundle);
        BooleanTestObject restored = new BooleanTestObject();
        Fridge.restoreInstanceState(restored, bundle);

        assertEquals(false, restored.boolValueNoAnnotation);
        assertEquals(testObject.boolValue, restored.boolValue);
        assertNotNull(restored.boolArrayValue);
        assertArrayEquals(testObject.boolArrayValue, restored.boolArrayValue);
        assertNotNull(restored.booleanValue);
        assertEquals(testObject.booleanValue, restored.booleanValue);

        testObject = new BooleanTestObject();
        testObject.boolArrayValue = null;
        testObject.booleanValue = null;

        bundle = new Bundle();
        Fridge.saveInstanceState(testObject, bundle);
        restored = new BooleanTestObject();
        Fridge.restoreInstanceState(restored, bundle);
        assertNull(restored.boolArrayValue);
        assertNull(restored.booleanValue);
    }

    @Test
    public void testSerializableSaveRestore() throws Exception {
        SerializableTestObject testObject = new SerializableTestObject();
        Bundle bundle = new Bundle();
        Fridge.saveInstanceState(testObject, bundle);
        SerializableTestObject restored = new SerializableTestObject();
        Fridge.restoreInstanceState(restored, bundle);

        assertNull(restored.serializableObject);

        SerializableObject serializableObject = new SerializableObject();
        serializableObject.num = 999;
        serializableObject.text = "hoge";
        testObject = new SerializableTestObject();
        testObject.serializableObject = serializableObject;
        bundle = new Bundle();
        Fridge.saveInstanceState(testObject, bundle);
        restored = new SerializableTestObject();
        Fridge.restoreInstanceState(restored, bundle);

        assertNotNull(restored.serializableObject);
        assertEquals(restored.serializableObject, serializableObject);
    }

    @Test
    public void testParcelableSaveRestore() throws Exception {
        ParcelableObject testObject = new ParcelableObject();
        testObject.parcelableTestObject = new ParcelableTestObject(999, "abc");
        testObject.parcelableTestObjects = new ParcelableTestObject[]{
                new ParcelableTestObject(1, "bbc"),
                new ParcelableTestObject(2, "cnn")
        };

        Bundle bundle = new Bundle();
        Fridge.saveInstanceState(testObject, bundle);
        ParcelableObject restored = new ParcelableObject();
        Fridge.restoreInstanceState(restored, bundle);

        assertNotNull(restored.parcelableTestObject);
        assertEquals(testObject.parcelableTestObject.num, restored.parcelableTestObject.num);
        assertEquals(testObject.parcelableTestObject.text, restored.parcelableTestObject.text);
        assertNotNull(restored.parcelableTestObjects);
        assertEquals(2, restored.parcelableTestObjects.length);
        assertEquals(testObject.parcelableTestObjects[0].num,
                restored.parcelableTestObjects[0].num);
        assertEquals(testObject.parcelableTestObjects[0].text,
                restored.parcelableTestObjects[0].text);
        assertEquals(testObject.parcelableTestObjects[1].num,
                restored.parcelableTestObjects[1].num);
        assertEquals(testObject.parcelableTestObjects[1].text,
                restored.parcelableTestObjects[1].text);

        testObject = new ParcelableObject();

        bundle = new Bundle();
        Fridge.saveInstanceState(testObject, bundle);
        restored = new ParcelableObject();
        Fridge.restoreInstanceState(restored, bundle);
        assertNull(restored.parcelableTestObject);
        assertNull(restored.parcelableTestObjects);
    }

    static class IntegerTestObject {

        private int intNumberNoAnnotation;

        @State
        private int intNumber;

        @State
        private int[] intArrayNumber;

        @State
        private ArrayList<Integer> intArrayListNumber;

        @State
        private Integer integerNumber;
    }

    static class BooleanTestObject {

        private boolean boolValueNoAnnotation;

        @State
        private boolean boolValue;

        @State
        private boolean[] boolArrayValue;

        @State
        private Boolean booleanValue;
    }

    static class SerializableObject implements Serializable {

        int num;

        String text;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            SerializableObject that = (SerializableObject) o;

            if (num != that.num) {
                return false;
            }
            return text != null ? text.equals(that.text) : that.text == null;

        }

        @Override
        public int hashCode() {
            int result = num;
            result = 31 * result + (text != null ? text.hashCode() : 0);
            return result;
        }
    }

    static class SerializableTestObject {

        @State
        private SerializableObject serializableObject;
    }

    static class ParcelableTestObject implements Parcelable {

        private int num;

        private String text;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int flags) {
            parcel.writeInt(num);
            parcel.writeString(text);
        }

        ParcelableTestObject(int num, String text) {
            this.num = num;
            this.text = text;
        }

        private ParcelableTestObject(Parcel in) {
            num = in.readInt();
            text = in.readString();
        }

        public static final Creator<ParcelableTestObject> CREATOR
                = new Creator<ParcelableTestObject>() {
            @Override
            public ParcelableTestObject createFromParcel(Parcel parcel) {
                return new ParcelableTestObject(parcel);
            }

            @Override
            public ParcelableTestObject[] newArray(int size) {
                return new ParcelableTestObject[size];
            }
        };
    }

    static class ParcelableObject {

        @State
        private ParcelableTestObject parcelableTestObject;

        @State
        private ParcelableTestObject[] parcelableTestObjects;
    }
}
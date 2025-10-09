package com.joffer.organizeplus.core.storage

class FakeSecureKeyValueStorage : SecureKeyValueStorage {
    
    private val storage = mutableMapOf<String, Any>()

    override fun putString(key: String, value: String) {
        storage[key] = value
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return storage[key] as? String ?: defaultValue
    }

    override fun putInt(key: String, value: Int) {
        storage[key] = value
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return storage[key] as? Int ?: defaultValue
    }

    override fun putLong(key: String, value: Long) {
        storage[key] = value
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return storage[key] as? Long ?: defaultValue
    }

    override fun putFloat(key: String, value: Float) {
        storage[key] = value
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return storage[key] as? Float ?: defaultValue
    }

    override fun putBoolean(key: String, value: Boolean) {
        storage[key] = value
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return storage[key] as? Boolean ?: defaultValue
    }

    override fun contains(key: String): Boolean {
        return storage.containsKey(key)
    }

    override fun remove(key: String) {
        storage.remove(key)
    }

    override fun clear() {
        storage.clear()
    }

    fun reset() {
        storage.clear()
    }
}


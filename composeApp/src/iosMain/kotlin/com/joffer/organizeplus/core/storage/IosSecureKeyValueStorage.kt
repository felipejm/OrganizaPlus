package com.joffer.organizeplus.core.storage

import platform.Foundation.NSUserDefaults

/**
 * iOS implementation using NSUserDefaults.
 * Based on KMP secure storage best practices from:
 * https://proandroiddev.com/how-to-store-key-values-in-kmp-in-the-secure-way-7614130dc4a9
 */
class IosSecureKeyValueStorage : SecureKeyValueStorage {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun putString(key: String, value: String) {
        userDefaults.setObject(value, key)
    }

    override fun getString(key: String, defaultValue: String?): String? {
        return userDefaults.stringForKey(key) ?: defaultValue
    }

    override fun putInt(key: String, value: Int) {
        userDefaults.setInteger(value.toLong(), key)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return userDefaults.integerForKey(key).toInt()
    }

    override fun putLong(key: String, value: Long) {
        userDefaults.setObject(value, key)
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        return userDefaults.objectForKey(key) as? Long ?: defaultValue
    }

    override fun putFloat(key: String, value: Float) {
        userDefaults.setFloat(value, key)
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        return userDefaults.floatForKey(key)
    }

    override fun putBoolean(key: String, value: Boolean) {
        userDefaults.setBool(value, key)
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return userDefaults.boolForKey(key)
    }

    override fun contains(key: String): Boolean {
        return userDefaults.objectForKey(key) != null
    }

    override fun remove(key: String) {
        userDefaults.removeObjectForKey(key)
    }

    override fun clear() {
        val dictionary = userDefaults.dictionaryRepresentation()
        dictionary.keys.forEach { key ->
            userDefaults.removeObjectForKey(key as String)
        }
    }
}

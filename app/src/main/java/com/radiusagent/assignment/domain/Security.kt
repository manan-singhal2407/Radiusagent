package com.radiusagent.assignment.domain

import javax.crypto.KeyGenerator

const val ALGORITHM_AES = "AES"
private const val KEY_SIZE = 256

class Security {

    fun generatePassphrase(): ByteArray {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM_AES)
        keyGenerator.init(KEY_SIZE)
        return keyGenerator.generateKey().encoded
    }
}
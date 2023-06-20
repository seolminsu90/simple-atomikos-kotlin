package com.simple.atomikos.service

import com.simple.atomikos.repository.db1.SampleRepository
import com.simple.atomikos.entity.db1.Sample
import com.simple.atomikos.entity.db2.Item
import com.simple.atomikos.repository.db2.ItemRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SampleService(
    private val sampleRepository: SampleRepository,
    private val itemRepository: ItemRepository
) {

    @Transactional
    fun insertSample(error: String?) {
        val sampleName = "Sample " + generateRandomString(5)
        val sample = Sample(name = sampleName)

        val itemName = "Item " + generateRandomString(5)
        var item = Item(name = itemName)

        sampleRepository.save(sample)
        itemRepository.save(item)
        sampleRepository.save(sample)

        itemRepository.save(Item(name = "1234"))
        itemRepository.save(Item(name = "1235642"))
        itemRepository.save(Item(name = "123456454"))
        itemRepository.save(Item(name = "126856834"))

        error?.let {
            throw Exception("Error로 인해 이전 세개 모두 롤백되어야 합니다.");
        }

        itemRepository.save(item)
        sampleRepository.save(sample)
        itemRepository.save(item)
    }


    private fun generateRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') // 허용된 문자 범위
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
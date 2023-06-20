package com.simple.atomikos.service

import com.simple.atomikos.entity.db1.Sample
import com.simple.atomikos.entity.db2.Item
import com.simple.atomikos.entity.db3.Second
import com.simple.atomikos.repository.db1.SampleRepository
import com.simple.atomikos.repository.db2.ItemRepository
import com.simple.atomikos.repository.db3.SecondRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SampleService(
        private val sampleRepository: SampleRepository,
        private val itemRepository: ItemRepository,
        private val secondRepository: SecondRepository
) {

    // 글로벌 분산 트랜젝션 적용 테스트 (error로 진입 시 이기종 데이터베이스여도 전부 롤백된다)
    // rollbackFor 지정 안해도 되야하는데, 지정해야하네.
    @Transactional(transactionManager = "globalTxManager", rollbackFor = [Exception::class])
    fun insertSample(error: String?) {
        val sampleName = "Sample " + generateRandomString(5)
        val sample = Sample(id = 300, name = sampleName)

        val itemName = "Item " + generateRandomString(5)
        var itemTest = Item(name = itemName)

        var secondName = "Second " + generateRandomString(5)
        var secondTest = Second(name = secondName)

        sampleRepository.save(sample)
        itemRepository.save(itemTest)
        sampleRepository.save(sample)
        secondRepository.save(secondTest)

        println(sampleRepository.count())

        error?.let {
            throw Exception("Error로 인해 이전 세개 모두 롤백되어야 합니다.")
        }

        itemRepository.save(itemTest)
        sampleRepository.save(sample)
        itemRepository.save(itemTest)
        secondRepository.save(secondTest)
    }


    private fun generateRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') // 허용된 문자 범위
        return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
    }
}
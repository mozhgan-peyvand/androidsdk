package ir.part.sdk.ara.domain.version.interactors

import ir.part.sdk.ara.domain.document.repository.BarjavandRepository
import javax.inject.Inject

class SaveLastShownUpdateVersion @Inject constructor(
    private val repository: BarjavandRepository
) {
    operator fun invoke(version: Int?) {
        return repository.saveLastShownUpdateVersion(version)
    }
}
/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import ConcurrencyExtras
import CoreData

protocol FruittieRepository {
    func getData() -> AsyncStream<[Fruittie]>
}

class DefaultFruittieRepository: FruittieRepository {
    private let managedObjectContext: NSManagedObjectContext
    private let api: FruittieApi

    init(managedObjectContext: NSManagedObjectContext, api: FruittieApi) {
        self.managedObjectContext = managedObjectContext
        self.api = api
    }

    func getData() -> AsyncStream<[Fruittie]> {
        let context = managedObjectContext
        Task {
            let isEmpty = try await context.perform {
                try context.fetch(Fruittie.fetchRequest()).isEmpty
            }

            if isEmpty {
                let response = try await api.getData(pageNumber: 0)
                try await context.perform {
                    response.feed.forEach { newItem in
                        let fruittie = Fruittie(context: context)
                        fruittie.name = newItem.name
                        fruittie.fullName = newItem.fullName
                    }

                    try context.save()
                }
            }
        }

        return AsyncStream.resultsStream(
            request: Fruittie.fetchRequest(), in: context)
    }
}

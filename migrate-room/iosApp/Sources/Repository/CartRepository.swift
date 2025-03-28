import ConcurrencyExtras
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
import CoreData

protocol CartRepository {
    func addToCart(fruittie: Fruittie) async throws

    func getCartItems() -> AsyncStream<[CartItem]>
}

class DefaultCartRepository: CartRepository {
    private let managedObjectContext: NSManagedObjectContext

    init(managedObjectContext: NSManagedObjectContext) {
        self.managedObjectContext = managedObjectContext
    }

    func addToCart(fruittie: Fruittie) async throws {
        let context = managedObjectContext
        try await context.perform {
            let request = CartItem.fetchRequest()
            request.predicate = NSPredicate(format: "fruittie == %@", fruittie)
            let results = try context.fetch(request)

            if results.isEmpty {
                let newItem = CartItem(context: context)
                newItem.fruittie = fruittie
                newItem.count = 1
            } else {
                if results.count > 1 {
                    print(
                        "Warning: Multiple CartItem for Fruittie \(fruittie.name!)"
                    )
                }

                results.forEach {
                    $0.count += 1
                }
            }

            try context.save()
        }
    }

    func getCartItems() -> AsyncStream<[CartItem]> {
        return AsyncStream.resultsStream(
            request: CartItem.fetchRequest(), in: managedObjectContext)
    }
}

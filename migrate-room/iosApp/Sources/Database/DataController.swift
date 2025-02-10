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

import Combine
import CoreData
import sharedKit

class DataController: ObservableObject {
    let container = NSPersistentContainer(name: "Fruitties")

    init() {
        container.loadPersistentStores { description, error in
            if let error {
                print("Core Data failed to load: \(error.localizedDescription)")
            }
            print("CoreData Sqlite location", description.url?.path(percentEncoded: false) ?? "N/A")
        }

        let appDatabase = databaseBuilder().buildAppDatabase()
        appDatabase.cartDao()
        
    }
}

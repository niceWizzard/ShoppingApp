package org.nice.listview;

import java.util.Optional;

public record Item<T>(T item, Optional<String> addOptions) {
}

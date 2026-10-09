import HeraldCore
import HeraldLog
import Shared

/// An iOS rule, written in Swift: personal data never reaches this log. The label is the shared
/// Kotlin one, checked by type.
struct NoPersonalDataLogFactory: LogEventTrackerFactory {
    func create(_ event: any Event) throws -> Resolution<any LogEventTracker> {
        if event.kotlinEvent is PersonalDataEvent {
            return .dropped
        }
        return .declined
    }
}

import HeraldInterop
import Shared

/// Connects herald-kmp's shared code to herald-ios. Copy this file into your iOS app as is: it
/// can't ship in a package, because it imports your app's own shared framework.
final class HeraldKmpBridge: NSObject, IosHeraldBridge {
    private let bridge: HeraldBridge

    init(_ bridge: HeraldBridge) {
        self.bridge = bridge
    }

    func track(name: String, parameters: [String: IosBridgeValue], markers: Set<String>) {
        bridge.track(name: name, parameters: parameters.mapValues(Self.value), markers: markers)
    }

    func set(name: String, value: IosBridgeValue, isUserProperty: Bool) {
        bridge.set(name: name, value: Self.value(value), isUserProperty: isUserProperty)
    }

    func identify(userId: String) { bridge.identify(userId: userId) }

    func reset() { bridge.reset() }

    func start() { bridge.start() }

    func flush() { bridge.flush() }

    func setEnabled(enabled: Bool) { bridge.setEnabled(enabled) }

    private static func value(_ value: IosBridgeValue) -> BridgeValue {
        switch value.type {
        case .string: return .string(value.string)
        case .int_: return .int(Int(value.int_))
        case .double_: return .double(value.double_)
        default: return .bool(value.bool_)
        }
    }
}

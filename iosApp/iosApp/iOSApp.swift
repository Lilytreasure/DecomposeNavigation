import SwiftUI
import ComposeApp


@main
struct iOSApp: App {
    private var lifecycle = LifecycleRegistryKt.LifecycleRegistry()
    
    var body: some Scene {
        WindowGroup {
            GeometryReader { geo in
                ContentView(
                    lifecycle:lifecycle,
                    topSafeArea: Float(geo.safeAreaInsets.top),
                    bottomSafeArea: Float(geo.safeAreaInsets.bottom)
                )
                .ignoresSafeArea()
                .onTapGesture {
                    // Hide keyboard on tap outside of TextField
                    UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
                }
            }
            
        }
    }
}

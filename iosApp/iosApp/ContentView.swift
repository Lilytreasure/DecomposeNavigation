import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    
    private let lifecycle: LifecycleRegistry
    private let topSafeArea: Float
    private let bottomSafeArea: Float
    
    init(lifecycle: LifecycleRegistry, topSafeArea: Float, bottomSafeArea: Float) {
        self.lifecycle = lifecycle
        self.topSafeArea = topSafeArea
        self.bottomSafeArea = bottomSafeArea
    }
    
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            lifecycle: lifecycle,
            topSafeArea: topSafeArea,
            bottomSafeArea: bottomSafeArea
        )

    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
   

}

struct ContentView: View {
    private let lifecycle: LifecycleRegistry
    private let topSafeArea: Float
    private let bottomSafeArea: Float
    
    
    init(lifecycle: LifecycleRegistry, topSafeArea: Float, bottomSafeArea: Float) {
        self.lifecycle = lifecycle
        self.topSafeArea = topSafeArea
        self.bottomSafeArea = bottomSafeArea
    }
    var body: some View {
        ComposeView( lifecycle: lifecycle,
                     topSafeArea: topSafeArea,
                     bottomSafeArea: bottomSafeArea)
        .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}







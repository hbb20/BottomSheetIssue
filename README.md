# Google Issue Tracker
[Reported Issue](https://issuetracker.google.com/issues/276375124)

# BottomSheetIssue
The progress reported is not smooth for initial swipe of bottomsheet.

After updating compose-bom from 2023.01.00 (which uses 1.3.3 of compose-material) to 2023.03.00 (which uses 1.4.0 of compose-material) I started seeing issues with the bottom sheet progress callback.

My usecase: I need to calculate open-fraction of bottomsheet (i.e. when it's totally collapsed to it's peek height then open fraction is 0.0f and when it's fully expanded, open-fraction should be 1.0f).

Now based on this I need to derive few things like corner size, alpha and offsets of a few other elements. In short, if open-fraction is not reliable then UI will not look smooth and will feel jumping.

With 1.4.0 of compose-material, Bottomsheet started using `SwipeableV2State` which required quite some change for my usecase to work. Even then, the reported progress is not what I expected.
After I start swipping up, the progress remains 0.0f until a certain point (I believe the point is `SwipeableV2State.positionalThreshold` but not sure). After that point, reported progress is directly from `0.0` to `0.075` and skips progress in between.


https://user-images.githubusercontent.com/4918760/229168786-67611a02-2151-4b3a-a8bc-5944dec3f4f6.mov


Same thing happens when I start to collapse sheet from expanded state.

https://user-images.githubusercontent.com/4918760/229168780-b9cd6b93-1890-49cb-bde8-4fcb68c73c02.mov

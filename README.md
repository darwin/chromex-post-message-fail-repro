Chromex `post-message!` fail repro

To reproduce:
1. Run `lein fig` from CLI
2. "Load unpacked" from Chrome in `/resources/unpacked` directory
3. Inspect `background.html` page console
4. "Inspect popup"
5. Click "Submit" in the popup UI
6. (optional) Uncomment top `go-loop` inside `handle-client-connect` to confirm the port works

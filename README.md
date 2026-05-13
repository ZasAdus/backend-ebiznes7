## Git hooks

To run detekt before each commit, enable the tracked hook directory:

```bash
git config core.hooksPath .githooks
```

The pre-commit hook runs `./gradlew :app:detekt` from the backend root.

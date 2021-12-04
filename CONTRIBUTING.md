## Contributing

It's super awesome to hear you're wishing to contribute to the project! Before you open a pull request, you'll need to
give a quick read to the following guidelines.

### Contributor License Agreement (CLA)

By submitting changes to this repository, you are hereby agreeing that:

- Your contributions will be licensed irrecoverably under the [GNU GPLv3](https://www.gnu.org/licenses/gpl-3.0.html).
- Your contributions are of your own work and free of legal restrictions (such as patents and copyrights) or other
issues which would pose issues for inclusion or distribution under the above license.

If you do not agree to these terms, please do not submit contributions to this repository. If you have any questions
about these terms, feel free to get in contact with me through the [public Discord server](https://discord.gg/mqHHsfT9fY) or
through opening an issue.

### Code Style

When contributing source code changes to the project, ensure that you make consistent use of the code style guidelines
used throughout the codebase (which follow pretty closely after the standard Java code style guidelines). These guidelines
have also been packaged as EditorConfig and IDEA inspection profiles which can be found in the repository root and `idea`
directory respectively.

- Use 4 spaces for indentations, not tabs. Avoid lines which exceed 120 characters.
- Use `this` to qualify member and field access.
- Always use braces when writing if-statements and loops.
- Annotate overriding methods with `@Override` so that breaking changes when updating will create hard compile errors.
- Comment code which needs to mimic vanilla behavior with `[VanillaCopy]` so it can be inspected when updating.

### Making a Pull Request

Your pull request should include a brief description of the changes it makes and link to any open issues which it
resolves. You should also ensure that your code is well documented where non-trivial and that it follows the
outlined code style guidelines above.

Additionally, if you're making changes for the sake of improving performance in either the vanilla game or the project
itself using mixin, try to provide a detailed test-case and benchmark for them. It's understandable that micro-benchmarking is
difficult in the context of Minecraft, but even naive figures taken from a profiler, timings graph, or a simple counter
will be greatly appreciated and help track incremental improvements. 

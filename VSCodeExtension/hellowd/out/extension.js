'use strict';
Object.defineProperty(exports, "__esModule", { value: true });
// The module 'vscode' contains the VS Code extensibility API
// Import the module and reference it with the alias vscode in your code below
const vscode = require("vscode");
const vscode_1 = require("vscode");
// this method is called when your extension is activated
// your extension is activated the very first time the command is executed
function activate(context) {
    // Use the console to output diagnostic information (console.log) and errors (console.error)
    // This line of code will only be executed once when your extension is activated
    console.log('Congratulations, your extension "hellowd" is now active!');
    // The command has been defined in the package.json file
    // Now provide the implementation of the command with  registerCommand
    // The commandId parameter must match the command field in package.json
    let disposable = vscode.commands.registerCommand('extension.sayHello', () => {
        // The code you place here will be executed every time your command is executed
        // Display a message box to the user
        vscode.window.showInformationMessage('Hello World!');
    });
    context.subscriptions.push(disposable);
    let sayName = vscode.commands.registerCommand('extension.sayName', () => {
        vscode.window.showInformationMessage("my name is zhujinshan");
    });
    context.subscriptions.push(sayName);
    // 统计字符个数
    let wordCounter = new WordCounter();
    let controller = new WordCounterController(wordCounter);
    let countWord = vscode.commands.registerCommand('extension.countWord', () => {
        vscode.window.showInformationMessage("my name is zhujinshan");
        wordCounter.updateWordCount();
    });
    context.subscriptions.push(countWord);
    context.subscriptions.push(controller);
    context.subscriptions.push(wordCounter);
    // 创建自定义输出
    let log = vscode.window.createOutputChannel("hellowdlog");
    log.show();
    log.append("this is test log out ---- \n");
    // 创建终端
    let ternimal = vscode.window.createTerminal("hellowd");
    ternimal.show();
    ternimal.sendText("cmd");
    // 创建状态栏按钮
    let btn = vscode.window.createStatusBarItem(vscode.StatusBarAlignment.Left, 7);
    btn.command = "extension.sayName"; //与package.json相同
    btn.text = "$(bug)按钮";
    btn.tooltip = "this is btn tips";
    btn.show();
    // 创建选项列表
    // let items: vscode.QuickPickItem[] = [];
    // items.push({label: "item111", description: "The item 111"});
    // items.push({label: "item222", description: "The item 222"});
    // let chose: vscode.QuickPickItem | undefined = vscode.window.showQuickPick(items);
    // if (chose) {
    //     btn.text = chose.label;
    // }
}
exports.activate = activate;
class WordCounter {
    constructor() {
        this._statusBarItem = vscode_1.window.createStatusBarItem(vscode_1.StatusBarAlignment.Left);
    }
    updateWordCount() {
        let editor = vscode_1.window.activeTextEditor;
        if (!editor) {
            this._statusBarItem.hide();
            return;
        }
        let doc = editor.document;
        if (doc.languageId === "markdown") {
            let wordCount = this._getWordCount(doc);
            this._statusBarItem.text = wordCount !== 1 ? `$(pencil) ${wordCount} Words` : `$(pencil) 1 word`; //显示一个pencil图标 具体有哪些图标，参考 https://octicons.github.com/
            this._statusBarItem.show();
        }
        else {
            this._statusBarItem.hide();
        }
    }
    _getWordCount(doc) {
        let docContent = doc.getText();
        docContent = docContent.replace(/(< ([^>]+)<)/g, '').replace(/\s+/g, ' ');
        docContent = docContent.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
        let wordCount = 0;
        if (docContent !== "") {
            wordCount = docContent.split(" ").length;
        }
        return wordCount;
    }
    dispose() {
        this._statusBarItem.dispose();
    }
}
class WordCounterController {
    constructor(wordCounter) {
        this._wordCounter = wordCounter;
        let subscriptions = [];
        vscode_1.window.onDidChangeTextEditorSelection(this._onEvent, this, subscriptions);
        vscode_1.window.onDidChangeActiveTextEditor(this._onEvent, this, subscriptions);
        this._wordCounter.updateWordCount();
        this._disposable = vscode_1.Disposable.from(...subscriptions);
    }
    dispose() {
        this._disposable.dispose();
    }
    _onEvent() {
        this._wordCounter.updateWordCount();
    }
}
// this method is called when your extension is deactivated
function deactivate() {
}
exports.deactivate = deactivate;
//# sourceMappingURL=extension.js.map
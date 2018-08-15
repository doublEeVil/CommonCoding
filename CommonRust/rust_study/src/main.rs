// rust 语法部分学习
// Date: 2018-08-03

/**
 * 这个是代码注释
 * 包括上文的
 * 和C语言家族(c++, java, c#, ...)相同
 */

/// 这种注释支持markdown
/// + 111 
/// + 222

//！ 它经常用在 crate 根文件（lib.rs）或者模块根文件（mod.rs）
//！

// 如果安装rust出现这样的提示 `error: no default toolchain configured`
// 如果是mac
// 输入以下
// `rustup toolchain link mygnutoolchain '/usr/local'`
// `rustup default mygnutoolchain`
// 即可
fn main() {
    println!("=== start ===");

    // 0. 变量绑定
    // 这里不会报错，但会编译警告`unused variable:`，提示变量未使用， 如果是go 将不能编译通过
    // 使用let开头的，记忆中有javascript支持， go 是用var
    let unuse_x: i32; 

    // 变量可以被隐藏，这意味着一个后声明的并位于同一作用域的相同名字的变量绑定将会覆盖前一个变量绑定
    let use_x = 8;
    println!("x = {}", use_x);
    let use_x = 15;
    println!("x = {}", use_x);

    // 1. 基础函数
    print_num1(23);
    println!("num add is {}", print_num2(1, 0));
    // diverges();
    // 函数指针
    let f_pointer: fn(i32, i32) -> i32;
    let f_pointer = print_num2;

    // 2. 原生类型
    // bool 只存在 true false
    let var_bool_true = true;
    let var_bool_false: bool = false;
    println!("var_bool_true is {}, {}", var_bool_true, var_bool_false);

    // char类型, 
    // char代表单独的unicode字符，占用空间是4 byte, 这一点与任何其他语言都不同
    let var_char_x = 'x';
    println!("char is {}", var_char_x);
    let var_char_y = 'π';
    println!("char is {}", var_char_y);

    // 数值类型，这一点与go很相似, i8,i16,i32,i64分别对应java中byte, short, int long
    // 而f32, f64则对应float以及doublele类型，当然，我觉得c++中的long long double这种类型rust应该是无能为力了
    // isize以及usize与底层硬件有关，自动分配位数
    let var_u8: u8 = 12;
    let var_u16: u16 = 13;
    let var_u32: u32 = 14;
    let var_u64: u64 = 15;

    let var_i8: i8 = -2;
    let var_i16: i16 = -6;
    let var_i32: i32 = -16;
    let var_i64: i64 = -64;

    let var_usize: usize = 89;
    let var_isize: isize = -45;

    let var_f32: f32 = -1.23;
    let var_f64: f64 = -45.6;

    // 数组, 与其他语言基本没有多大区别，唯一的大概就是长度是函数吧
    let var_arr_a = [1, 2, 3];
    println!("arr first is {}, len is {}", var_arr_a[0], var_arr_a.len());

    // 切片，python,lua,go等等，切片都是一个很重要的功能，其本质是一个数组的引用或者说视图
    // 切片的表示是.. go 中是用：
    let var_slice_a = &var_arr_a[..];
    let var_slice_b = &var_arr_a[1..2];

    // str, 原始的字符串类型，也是不定长类型，这个后面再讲
    
    // 元组，固定大小的有序列表，这个再Python中很常见, 直接使用顺序标号访问
    let var_tuples_a = (23, "zhujinshan");
    println!("tuples is {}, {}", var_tuples_a.0, var_tuples_a.1);

    // 3. if语句, 不带()和go一模一样
    if 8 == 5 {
    	println!("8 == 5 is true");
    } else if 8 > 9 {
    	println!("8 > 9 is true");
    } else {
    	println!("all is false");
    }

    let var_if_x = if 9 > 10 {
    	10
    } else {
    	23
    };
    println!("var is {}", var_if_x);

    // 4. 循环
    // rust提供3种循环 loop, while, for
    let mut var_loop_x = 1;
    loop {
    	println!("var_loop_x is {}", var_loop_x);
    	var_loop_x += 1;

    	if var_loop_x == 2 {
    		continue;
    	} else if var_loop_x == 3 {
    		break;
    	}
    }

    while true {
    	println!("var_loop_x is {}", var_loop_x);
    	var_loop_x += 1;

    	if var_loop_x == 5 {
    		continue;
    	} else if var_loop_x == 6 {
    		break;
    	}
    }

    // for循环
    // 不支持go中这样的死循环写法
    // for {
    // }
    
    // 范围是[0,2)
    for var_for_x in 0 .. 2{
    	println!("var_for_x is {}", var_for_x);
    }

    for (index, value) in (5 .. 7).enumerate() {
    	println!("index is {} value is {}", index, value);
    }

    let string_lines = "hello\nworld".lines();
    for line in string_lines {
    	println!("str is {}", line);
    }

    // 循环标签，还是第一次见这种语法，go c 等都有标签，但使用率很低
    'LB_OUT: for x in 0..10 {
    	'LB_IN: for y in 0..10 {
    		if x % 2 == 0 {continue 'LB_OUT;}
    		if y % 2 == 0 {continue 'LB_IN;}
    		println!("x:{} y:{}", x, y);
    	}
    }

    // 5. Vectors，类似Java中ArrayList,LinkedList
    // 数据存在堆内存
    let var_v1 = vec![1,2,3];//初始化
    let var_v2 = vec![1.5; 5];//初始化5个元素，值为1

    println!("var_v2 index 2 {}", var_v2[2]);
    // 处理越界访问
    match (var_v1.get(3)) {
        Some(x) => println!("item 3 is {}", x),
        None => println!("error")
    }
    // 迭代输出
    for i in &var_v1 {
        println!("----{}", i);
    }

    // 6. 所有权, 生命周期
    // Rust 确保了对于任何给定的资源都正好（只）有一个绑定与之对应
    // 
    // 下面的代码会编译出错
    // let a = vec![2];
    // let b = a;
    // println!("{}", a); 

    // 借用
    fn tt1(v1: &Vec<i32>) -> i32 {
        42
    }

    let tret = tt1(&var_v1);

    let mut var_t = 5;
    {
        let var_y = &mut var_t; 
        *var_y += 1;
    }
    println!("{}", var_t); //结果是6

    // 下面代码不能编译过
    // let y: &i32;
    // let x = 9;
    // y = &x;
    
    // 这种写法是可以的 
    let x = 9;
    let y: &i32;
    y = &x;

    // 尽量避免悬垂指针
    // 下面的代码会报错，应该避免这种情况
    // let r;
    // {
    //     let i = 9;
    //     r = &i;
    // }
    // println!("{}", r); // r指向i, 但i 已经销毁了。所以报错
    
    // 更复杂的例子, 下面的例子报错，不知道为何
    // fn skip_prefix<'a, 'b>(line: &'a str, prefix: &'b str) ->&'a str {
    // # line
    // }
    // let line = "lang:en=Hello World!";
    // let lang = "en";

    // let v;
    // {
    //     let p = format!("land:{}=", lang);
    //     v = skip_prefix(line, p.as_str());
    // }
    // println!("{}", v);

    // 结构体对象，显示生命周期, 必须是引用用法
    struct Foo1<'a> {
        x: &'a i32,
    }
    struct Foo2 {
        x: i32,
    }

    let y1 = &5;
    let f1 = Foo1 {x: y1};
    println!("=={}", f1.x);

    let y2 = 8;
    let f2 = Foo2 {x: y2};
    println!("=={}", f2.x);


    // impl块, 和go 的interface有异曲同工之妙
    struct Foo<'a> {
        x: &'a i32,
    }

    impl <'a> Foo<'a> {
        fn x(&self) -> &'a i32 {self.x}
    }

    let y = &7;
    let f = Foo{x: y};
    println!("x is : {}", f.x());

    // static的生命周期， 和java的static一致
    let x: &'static str = ";;;";
    println!("{}", x);

    // 等价与这样的写法
    static xx: i32 = 44;
    let x1 : &'static i32 = &xx;
    println!("{}", x1);

    // 7. 可变性
    // 下面的写法是错误的
    // let x = 5;
    // x = 6; // Error!
    // 
    
    // 8. 结构体
    struct Point {
        x: i32,
        y: i32
    }

    let p1 = Point{x: 0, y:9};
    println!("{},{}", p1.x, p1.y);

    let mut p2 = Point{x:2, y:9};
    p2.y = 12;
    println!("{}", p2.y);
    println!("{}", p2.y);

    // 以下是可以快速拷贝值
    let mut p3 = Point {x:8, y:9};
    let p4 = Point {x: 9, .. p3}; // 等同于快速从p3那里拷贝值
    println!("p4==({},{})", p4.x, p4.y);

    // 8.1 元组，可以看成结构体的特殊形式
    struct Color (i32, i32, i32);
    let black = Color(0,0,0);
    println!("b.r={}", black.0);

    // 类单元结构体, 下面的写法是可以的
    struct P1;

    let p = P1;

    // 9. 枚举， 和java完全不同，和c++，go也看不出有哪里相似的地方
    enum Msg {
        Quit,
        Move {x: i32, y:i32},
        Chat (String)
    }

    let x:Msg = Msg::Move{x:3, y:7};
    let m:Msg = Msg::Chat("ddd".to_string());

    // 10.匹配， 其他语言一般都是用switch
    let x = 5;
    let num = match x {
        1 => "one",
        2 => "two",
        3 => "three",
        4 | 5 => "four or five",
        _ => "other",
    };
    println!("{}", num);
}

// 1.1 基本格式
fn print_num1(num: i32) {
	println!("num is {}", num);
}

// 1.2 带返回值, 与go类似，但传参必须声明变量类型，哪怕所有变量都是同一个类型
// 提前返回可以是带return 
// 正常返回无需return, 在rust设计哲学中，带return 不是友好的设计
// 支持多值返回， go语言支持，lua等也支持
fn print_num2(num1: i32, num2: i32) -> i32 {
	if num2 == 0 {
		return 0
	}
	num2 + num1
}

fn print_num3(num1: i32, num2: i32) -> (i32, i32) {
    (num2, num1)
}

// 1.3 发散函数
// 发散函数可以用作任何类型
// 不是很清楚具体含义以及使用场景
fn diverges() ->! {
	panic!("This function never return...");
}

import os
import chardet
import time
from datetime import datetime

# 配置部分
FILE_EXTENSIONS = ['.py', '.properties', '.yml', '.java', '.xml']  # 支持的文件扩展名
# FILE_EXTENSIONS = ['.properties', '.yml', '.java']  # 支持的文件扩展名
# FILE_EXTENSIONS = ['.xml']  # 支持的文件扩展名
# FOLDER_PATH = r'E:\idea\java\springboot1.5.x\DimPlatform'  # 输入文件夹路径
FOLDER_PATH = r'E:\idea\java\springboot1.5.x\DimPlatform\DimPlatformGW'  # 输入文件夹路径
FOLDER_PATH = r'C:\data\idea\springboot1.5.22\DimPlatform\TestCron'  # 输入文件夹路径
OUTPUT_FILE = 'tmp/code_{}.md'.format(datetime.now().strftime('%Y%m%d%H%M%S'))  # 输出文件名，带时间戳
BUFFER_SIZE = 4096  # 缓冲区大小

# 问题描述
ISSUE_DESCRIPTION = "在使用docker-compose部署XXX应用时遇到了XXX错误。"

# 环境信息
ENV_INFO = """
- 操作系统: Windows 10
- Docker版本: 20.10.7
- docker-compose版本: 1.29.2
"""

# 错误日志
ERROR_LOG = """
[粘贴terminal中的错误日志]
"""

# 其他说明
OTHER_INFO = """
[如有其他需要说明的信息,可以在这里补充]
"""

def get_directory_structure(folder_path, base_path):
    """
    获取目录结构
    :param folder_path: 文件夹路径
    :param base_path: 基础路径，用于计算相对路径
    :return: 目录结构字符串和路径列表
    """
    structure = ["[toc]\n"]  # Markdown目录生成插件
    paths = []
    for root, dirs, files in os.walk(folder_path):
        # 跳过 target 目录
        if 'target' in root.split(os.sep):
            continue
        relative_root = os.path.relpath(root, base_path)
        if relative_root == '.':
            structure.append(f"- 根目录")
        else:
            structure.append(f"- {relative_root.replace(os.sep, '-')}")
        for file in files:
            if any(file.endswith(ext) for ext in FILE_EXTENSIONS):
                relative_file_path = os.path.join(relative_root, file)
                display_path = os.path.join(relative_root, os.path.splitext(file)[0])
                anchor = display_path.replace(' ', '-').replace('\\', '-').replace('/', '-')
                if relative_root == '.':
                    anchor = os.path.splitext(file)[0].replace(' ', '-')
                structure.append(f"  - [{file}](#{anchor})")
                paths.append((relative_file_path, display_path))
    return '\n'.join(structure), paths

def merge_files(folder_path, out_file, base_path):
    """
    将文件夹中的所有代码文件内容合并到一个txt文件中
    :param folder_path: 文件夹路径
    :param out_file: 输出文件对象
    :param base_path: 基础路径，用于计算相对路径
    :return: 写入文件数量
    """
    count = 0  # 记录写入的文件数量
    # 获取文件夹中的文件列表
    file_list = os.listdir(folder_path)
    # 按照名称排序
    file_list.sort()
    for f in file_list:
        # 获取文件路径
        file_path = os.path.join(folder_path, f)

        # 如果是文件夹，则递归处理
        if os.path.isdir(file_path):
            # 跳过 target 目录
            if 'target' in file_path.split(os.sep):
                continue
            count += merge_files(file_path, out_file, base_path)
        # 如果是代码文件，则添加到txt中
        elif any(f.endswith(ext) for ext in FILE_EXTENSIONS):
            print(f"正在处理文件：{file_path}")
            # 获取相对路径
            relative_path = os.path.relpath(file_path, base_path)
            display_path = os.path.join(os.path.dirname(relative_path), os.path.splitext(f)[0])
            anchor = display_path.replace(' ', '-').replace('\\', '-').replace('/', '-')
            if relative_path == '.':
                anchor = os.path.splitext(f)[0].replace(' ', '-')
            # 读取文件内容
            try:
                with open(file_path, mode='r', encoding='utf-8') as code_file:
                    content = code_file.read()
            except UnicodeDecodeError as e:
                encoding = chardet.detect(open(file_path, 'rb').read(BUFFER_SIZE))['encoding']  # 获取文件编码方式
                print(f"Error: {e}, skip {file_path}")
                # 尝试使用文件编码方式再次读取文件
                try:
                    with open(file_path, mode='r', encoding=encoding) as code_file:
                        content = code_file.read()
                        # 将*替换掉无法解析的中文
                        content = content.replace("\uFFFD", "*")
                except Exception as e1:
                    print(f"Error: {e1}, skip {file_path}")
                    continue
            # 添加文件路径和标题到txt中
            out_file.write(f"### {anchor}\n")
            out_file.write(f"#### {relative_path}\n\n")
            # 将代码添加到txt中
            content = content.replace('\t', ' ' * 4)  # 将制表符替换为四个空格
            out_file.write(f"```{os.path.splitext(file_path)[1][1:]}\n{content}\n```\n\n")
            # 使用缓冲区和sleep保证所有内容能够写入文件
            out_file.flush()
            os.fsync(out_file.fileno())
            count += 1

    time.sleep(0.1)
    return count

if __name__ == '__main__':
    if os.getpid() == 0:
        print("当前程序以系统权限运行")
    else:
        print("当前程序未以系统权限运行")
    # 如果文件不存在，则创建一个
    if not os.path.exists(OUTPUT_FILE):
        open(OUTPUT_FILE, 'w').close()
    # 增加写入权限
    os.chmod(OUTPUT_FILE, 0o777)
    # 打开或新建txt文件
    with open(OUTPUT_FILE, mode='a+', encoding='utf-8') as out_file:
        # 输出问题描述到文件开头
        out_file.write("## 问题描述\n")
        out_file.write(f"{ISSUE_DESCRIPTION}\n\n")

        # 输出环境信息到文件
        out_file.write("## 环境信息\n")
        out_file.write(f"{ENV_INFO}\n\n")

        # 输出错误日志到文件
        out_file.write("## 错误日志\n")
        out_file.write(f"{ERROR_LOG}\n\n")

        # 输出其他说明到文件
        out_file.write("## 其他说明\n")
        out_file.write(f"{OTHER_INFO}\n\n")

        # 输出目录结构到文件
        directory_structure, paths = get_directory_structure(FOLDER_PATH, FOLDER_PATH)
        out_file.write("# 目录结构\n")
        out_file.write(f"{directory_structure}\n\n")
        out_file.write(f"{'=' * 50}\n\n")
        # 合并文件
        count = merge_files(FOLDER_PATH, out_file, FOLDER_PATH)
        print(f"共写入 {count} 个文件。")

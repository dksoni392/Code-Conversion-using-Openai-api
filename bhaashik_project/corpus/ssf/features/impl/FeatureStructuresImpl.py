import re
from typing import List

class ReadRecurseArgs:
    def __init__(self, fs_str, level, position, fa):
        self.str = fs_str
        self.level = level
        self.position = position
        self.fa = fa

class FeatureStructuresImpl:
    fsProps = None

    def __init__(self, user_object=None, allows_children=True):
        super().__init__(user_object, allows_children)

    @staticmethod
    def get_fs_properties():
        if FeatureStructuresImpl.fsProps is None:
            FeatureStructuresImpl.load_fs_properties()
        return FeatureStructuresImpl.fsProps

    @staticmethod
    def load_fs_properties():
        FeatureStructuresImpl.fsProps = FSProperties()
        try:
            FeatureStructuresImpl.fsProps.read("props/fs-mandatory-attribs.txt",
                                              "props/fs-other-attribs.txt",
                                              "props/fs-props.txt",
                                              "props/ps-attribs.txt",
                                              "props/dep-attribs.txt",
                                              "props/sem-attribs.txt",
                                              "UTF-8")
        except FileNotFoundError as ex:
            print(ex)
        except IOError as ex:
            print(ex)

class FeatureAttribute:
    def __init__(self):
        self.name = ""
        self.alt_values = []

    def set_name(self, name):
        self.name = name

    def add_alt_value(self, alt_value):
        self.alt_values.append(alt_value)

    def count_alt_values(self):
        return len(self.alt_values)

    def get_name(self):
        return self.name

    def get_alt_value(self, index):
        return self.alt_values[index]

class FeatureAttributeImpl(FeatureAttribute):
    pass

class FeatureStructureImpl:
    def __init__(self):
        self.version = 0

    def read_recurse(self, args):
        pass  # Implement the method as needed

class FeatureValueImpl:
    def __init__(self, value):
        self.value = value

    def get_value(self):
        return self.value

class FSProperties:
    def get_property_value(self, property_key):
        pass  # Implement the method as needed

    def get_property_value_for_print(self, property_key):
        pass  # Implement the method as needed

class FeatureStructuresImpl(BhaashikMutableTreeNode, FeatureStructures, BhaashikDOMElement, Serializable):
    fsProps = None

    def __init__(self, user_object=None, allows_children=True):
        super().__init__(user_object, allows_children)

    def count_alt_fs_values(self):
        return self.get_child_count()

    def add_alt_fs_value(self, feature_structure):
        self.add(feature_structure)
        return self.get_child_count()

    def get_alt_fs_value(self, num):
        return self.get_child_at(num)

    def modify_alt_fs_value(self, feature_structure, index):
        self.insert(feature_structure, index)
        self.remove(index + 1)

    def find_alt_fs_value(self, feature_structure):
        return self.get_index(feature_structure)

    def remove_alt_fs_value(self, num):
        removed_fs = self.get_alt_fs_value(num)
        self.remove(num)
        return removed_fs

    @staticmethod
    def get_fs_properties():
        if FeatureStructuresImpl.fsProps is None:
            FeatureStructuresImpl.load_fs_properties()
        return FeatureStructuresImpl.fsProps

    @staticmethod
    def load_fs_properties():
        FeatureStructuresImpl.fsProps = FSProperties()
        try:
            FeatureStructuresImpl.fsProps.read(GlobalProperties.resolve_relative_path("props/fs-mandatory-attribs.txt"),
                                              GlobalProperties.resolve_relative_path("props/fs-other-attribs.txt"),
                                              GlobalProperties.resolve_relative_path("props/fs-props.txt"),
                                              GlobalProperties.resolve_relative_path("props/ps-attribs.txt"),
                                              GlobalProperties.resolve_relative_path("props/dep-attribs.txt"),
                                              GlobalProperties.resolve_relative_path("props/sem-attribs.txt"),
                                              "UTF-8")
        except FileNotFoundError as ex:
            print(ex)
        except IOError as ex:
            print(ex)

    def read_string(self, fs_str):
        self.clear()
        fs_or = self.get_fs_properties().get_properties().get_property_value_for_print("fsOR")
        fs_strings = fs_str.split("|")
        for fs_string in fs_strings:
            if fs_string and fs_string != "":
                fs = FeatureStructureImpl()
                pos = fs.read_string(fs_string)
                fs.check_and_set_has_mandatory()
                if pos != -1:
                    self.add_alt_fs_value(fs)

        return 0

    def print_fs_ssf(self, fs):
        pass  # Implement the method as needed

    def print(self, ps):
        ps.println(self.make_string())

    def make_string_fv(self):
        if self.count_alt_fs_values() == 0:
            return ""
        return self.get_alt_fs_value(0).make_string_fv()

    def make_string(self):
        version = 2
        if self.get_depth() > 3:
            version = 1
        result_str = ""
        for i in range(self.count_alt_fs_values()):
            fs = self.get_alt_fs_value(i)
            fs.version = version
            result_str += fs.make_string()
            if i < self.count_alt_fs_values() - 1:
                result_str += self.get_fs_properties().get_properties().get_property_value_for_print("fsOR")
        return result_str

    def make_string_for_rendering(self):
        version = 2
        if self.get_depth() > 3:
            version = 1
        result_str = ""
        for i in range(self.count_alt_fs_values()):
            fs = self.get_alt_fs_value(i)
            fs.version = version
            result_str += fs.make_string_for_rendering()
            if i < self.count_alt_fs_values() - 1:
                result_str += self.get_fs_properties().get_properties().get_property_value_for_print("fsOR")
        return result_str

    def get_copy(self):
        result_str = self.make_string()
        fss = FeatureStructuresImpl()
        fss.read_string(result_str)
        return fss

    def get_attribute_names(self):
        if self.count_alt_fs_values() == 0:
            return None
        return self.get_alt_fs_value(0).get_attribute_names()

    def get_attribute_values(self):
        if self.count_alt_fs_values() == 0:
            return None
        return self.get_alt_fs_value(0).get_attribute_values()

    def get_attribute_value_pairs(self):
        if self.count_alt_fs_values() == 0:
            return None
        return self.get_alt_fs_value(0).get_attribute_value_pairs()

    def get_attribute_value_string(self, attribute_name):
        if self.count_alt_fs_values() == 0:
            return None
        fs = self.get_alt_fs_value(0)
        if fs is None:
            return None
        fa = fs.get_attribute(attribute_name)
        if fa is None:
            return None
        elif fa.count_alt_values() == 0:
            return None
        else:
            fv = fa.get_alt_value(0)
            if fv is None:
                return None
            else:
                return fv.make_string()


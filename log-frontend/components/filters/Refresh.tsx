"use client";

import { useState } from "react";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import { Button } from "../ui/button";
import { Check, ChevronDownIcon } from "lucide-react";
import { Command, CommandGroup, CommandItem, CommandList } from "../ui/command";
import { cn } from "@/lib/utils";

const listState = [
  { key: "false", value: "Not refresh" },
  { key: "true", value: "Refresh" },
];

const StateRefresh = ({ onChange }: { onChange: (item: string) => void }) => {
  const [value, setValue] = useState("false");
  const [open, setOpen] = useState(false);

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant={"outline"}
          role="combobox"
          aria-expanded={open}
          className="w-50 justify-between"
        >
          {value
            ? listState.find((lv) => lv.key === value)?.value
            : "Find by level"}
          <ChevronDownIcon className="ml-2 h-4 shrink-0 opacity-50" />
        </Button>
      </PopoverTrigger>

      <PopoverContent className="w-50 p-0">
        <Command>
          <CommandList>
            <CommandGroup>
              {listState.map((lv) => (
                <CommandItem
                  key={lv.key}
                  value={lv.key}
                  onSelect={(currentValue) => {
                    setValue(currentValue === value ? "" : currentValue);
                    onChange(currentValue === value ? "false" : currentValue);
                    setOpen(false);
                  }}
                >
                  {lv.value}
                  <Check
                    className={cn(
                      "ml-auto",
                      value === lv.key ? "opacity-100" : "opacity-0"
                    )}
                  />
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
};

export default StateRefresh;

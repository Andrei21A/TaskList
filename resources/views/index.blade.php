@extends('layouts.app')

@section('title', 'The list of tasks')

@section('content')
    <nav class="mb-4">
        <a href="{{ route('tasks.create')
        }}" class="link"> Create </a>
    </nav>
    @forelse ($tasks as $task)
        <div>
            <a href="{{ route('tasks.show', ['task' => $task]) }}"
                @class(['font-bold', 'line-through' => $task->completed])
                > {{ $task->title }} </a>
        </div>
    @empty
        <p> There are no items on the list</p>
    @endforelse
    @if ($tasks->count()) 
    <nav class="mt-10">
        {{ $tasks -> links() }}
    </nav>
    @endif
@endsection
